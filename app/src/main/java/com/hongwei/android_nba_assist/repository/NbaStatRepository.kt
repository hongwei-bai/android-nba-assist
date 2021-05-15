package com.hongwei.android_nba_assist.repository

import com.hongwei.android_nba_assist.constant.AppConfigurations.ForceRefreshInterval
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.HttpCode
import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import com.hongwei.android_nba_assist.datasource.mapper.NbaStandingMapper.map
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamScheduleMapper.map
import com.hongwei.android_nba_assist.datasource.network.service.NbaStatService
import com.hongwei.android_nba_assist.datasource.room.Event
import com.hongwei.android_nba_assist.datasource.room.StandingDao
import com.hongwei.android_nba_assist.datasource.room.StandingEntity
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleDao
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getFirstDayOfWeek
import com.hongwei.android_nba_assist.view.main.DataStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val standingDao: StandingDao
) {
    private val dataStatusChannel = Channel<DataStatus>()

    val dataStatus = dataStatusChannel.consumeAsFlow()

    fun getNextGameInfo(team: String): Flow<Event> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            it.events.firstOrNull { event ->
                after(event.unixTimeStamp)
            }
        }.filterNotNull()
    }

    fun getTeamSchedule(team: String): Flow<List<Event>> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team, it?.dataVersion)
        }.filterNotNull().map {
            val dataMightExpire = it.timeStamp.dataMayOutdated()
            if (dataMightExpire) {
                dataStatusChannel.send(DataStatus.DataMayOutdated)
                fetchTeamScheduleFromBackend(team, it.dataVersion)
            }
            it.events.filter { eventTime ->
                eventTime.unixTimeStamp > getFirstDayOfWeek(AppSettings.weekStartsFromMonday).timeInMillis
            }
        }
    }

    fun getStanding(): Flow<StandingEntity> {
        return standingDao.getStanding().onEach {
            it ?: fetchStandingFromBackend(it?.dataVersion)
        }.filterNotNull().onEach {
            val dataMightExpire = it.timeStamp.dataMayOutdated()
            if (dataMightExpire) {
                dataStatusChannel.send(DataStatus.DataMayOutdated)
                fetchStandingFromBackend(it.dataVersion)
            }
        }
    }

    suspend fun fetchTeamScheduleFromBackend(team: String, dataVersion: Long? = null) {
        val response = nbaStatService.getTeamSchedule(team, dataVersion ?: -1)
        val data = response.body()
        when (response.code()) {
            HttpCode.HTTP_OK -> data?.let { teamScheduleDao.save(data.map(team)) }
            HttpCode.HTTP_DATA_UP_TO_DATE -> {
                dataStatusChannel.send(DataStatus.DataIsUpToDate)
                teamScheduleDao.update(System.currentTimeMillis())
            }
            else -> dataStatusChannel.send(DataStatus.ServiceError)
        }
    }

    suspend fun fetchStandingFromBackend(dataVersion: Long? = null) {
        val response = nbaStatService.getStanding(dataVersion ?: -1)
        val data = response.body()
        when (response.code()) {
            HttpCode.HTTP_OK -> data?.let { standingDao.save(data.map()) }
            HttpCode.HTTP_DATA_UP_TO_DATE -> {
                dataStatusChannel.send(DataStatus.DataIsUpToDate)
                standingDao.update(System.currentTimeMillis())
            }
            else -> dataStatusChannel.send(DataStatus.ServiceError)
        }
    }

    private fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))

    private fun Long.dataMayOutdated(): Boolean = System.currentTimeMillis() - this > ForceRefreshInterval.FOR_SCHEDULE_HOUR * MILLIS_PER_HOUR
}