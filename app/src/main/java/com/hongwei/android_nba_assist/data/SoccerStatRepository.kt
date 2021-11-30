package com.hongwei.android_nba_assist.data

import com.hongwei.android_nba_assist.AppConfigurations
import com.hongwei.android_nba_assist.data.local.AppSettings
import com.hongwei.android_nba_assist.data.mapper.SoccerTeamScheduleMapper.map
import com.hongwei.android_nba_assist.data.network.service.SoccerStatService
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamEvent
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamScheduleDao
import com.hongwei.android_nba_assist.data.util.DataValidationUtil.dataMayOutdated
import com.hongwei.android_nba_assist.ui.component.DataStatus
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil
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

    val dataStatus = dataStatusChannel.receiveAsFlow()

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