package com.mikeapp.sportsmate.data

import com.mikeapp.sportsmate.AppConfigurations
import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.data.mapper.SoccerTeamScheduleMapper.map
import com.mikeapp.sportsmate.data.network.service.SoccerStatService
import com.mikeapp.sportsmate.data.room.soccer.SoccerTeamEvent
import com.mikeapp.sportsmate.data.room.soccer.SoccerTeamScheduleDao
import com.mikeapp.sportsmate.data.util.DataValidationUtil
import com.mikeapp.sportsmate.data.util.DataValidationUtil.dataMayOutdated
import com.mikeapp.sportsmate.ui.component.DataStatus
import com.mikeapp.sportsmate.util.LocalDateTimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SoccerStatRepository @Inject constructor(
    private val soccerStatService: SoccerStatService,
    private val soccerTeamScheduleDao: SoccerTeamScheduleDao
) {
    private val dataStatusChannel = Channel<DataStatus>()

    val dataStatus: Flow<DataStatus> = dataStatusChannel.receiveAsFlow()

    fun getNextGameInfo(team: String): Flow<SoccerTeamEvent> {
        return soccerTeamScheduleDao.getSoccerTeamSchedule().onEach {
            it ?: fetchSoccerTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            it.events.firstOrNull { event ->
                DataValidationUtil.after(event.unixTimeStamp)
            }
        }.filterNotNull()
    }

    fun getTeamSchedule(team: String): Flow<List<SoccerTeamEvent>> {
        return soccerTeamScheduleDao.getSoccerTeamSchedule().onEach {
            when {
                it == null -> fetchSoccerTeamScheduleFromBackend(team)
                dataMayOutdated(it.timeStamp) -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchSoccerTeamScheduleFromBackend(team, it.dataVersion)
                }
            }
        }.filterNotNull().map {
            it.events.filter { eventTime ->
                eventTime.unixTimeStamp > LocalDateTimeUtil.getFirstDayOfWeek(AppSettings.weekStartsFromMonday).timeInMillis
            }
        }
    }

    suspend fun fetchSoccerTeamScheduleFromBackend(team: String, dataVersion: Long? = null) {
        withContext(Dispatchers.IO) {
            val response = soccerStatService.getTeamSchedule(team, dataVersion ?: -1)
            val data = response.body()
            when (response.code()) {
                AppConfigurations.Network.HttpCode.HTTP_OK -> data?.let { soccerTeamScheduleDao.save(data.map()) }
                AppConfigurations.Network.HttpCode.HTTP_DATA_UP_TO_DATE -> {
                    dataStatusChannel.send(DataStatus.DataIsUpToDate)
                    soccerTeamScheduleDao.update(System.currentTimeMillis())
                }
                else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch soccer schedules data error, code: ${response.code()}"))
            }
        }
    }
}