package com.hongwei.android_nba_assist.data

import com.hongwei.android_nba_assist.constant.AppConfigurations.ForceRefreshInterval
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.HttpCode
import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.data.local.AppSettings
import com.hongwei.android_nba_assist.data.mapper.NbaPlayOffMapper.map
import com.hongwei.android_nba_assist.data.mapper.NbaStandingMapper.map
import com.hongwei.android_nba_assist.data.mapper.NbaTeamScheduleMapper.map
import com.hongwei.android_nba_assist.data.network.service.NbaStatService
import com.hongwei.android_nba_assist.data.room.*
import com.hongwei.android_nba_assist.ui.component.DataStatus
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getFirstDayOfWeek
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val standingDao: StandingDao,
    private val playOffDao: PlayOffDao
) {
    private val dataStatusChannel = Channel<DataStatus>()

    val dataStatus = dataStatusChannel.receiveAsFlow()

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
            when {
                it == null -> fetchTeamScheduleFromBackend(team)
                it.dataMayOutdated() -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchTeamScheduleFromBackend(team, it.dataVersion)
                }
            }
        }.filterNotNull().map {
            it.events.filter { eventTime ->
                eventTime.unixTimeStamp > getFirstDayOfWeek(AppSettings.weekStartsFromMonday).timeInMillis
            }
        }
    }

    fun getStanding(): Flow<StandingEntity> {
        return standingDao.getStanding().onEach {
            when {
                it == null -> fetchStandingFromBackend()
                it.dataMayOutdated() -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchStandingFromBackend(it.dataVersion)
                }
            }
        }.filterNotNull()
    }

    fun getPlayOff(): Flow<PlayOffEntity> {
        return playOffDao.getPlayOff().onEach {
            when {
                it == null -> fetchPlayOffFromBackend()
                it.dataMayOutdated() -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchPlayOffFromBackend(it.dataVersion)
                }
            }
        }.filterNotNull()
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
            else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch schedules data error, code: ${response.code()}"))
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
            else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch standings data error, code: ${response.code()}"))
        }
    }

    suspend fun fetchPlayOffFromBackend(dataVersion: Long? = null) {
        val response = nbaStatService.getPlayOff(dataVersion ?: -1)
        val data = response.body()
        when (response.code()) {
            HttpCode.HTTP_OK -> data?.let {
                playOffDao.save(data.map())
            }
            HttpCode.HTTP_DATA_UP_TO_DATE -> {
                dataStatusChannel.send(DataStatus.DataIsUpToDate)
                playOffDao.update(System.currentTimeMillis())
            }
            else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch Playoff data error, code: ${response.code()}"))
        }
    }

    private fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))

    private fun TeamScheduleEntity.dataMayOutdated(): Boolean = System.currentTimeMillis() - this.timeStamp >
            ForceRefreshInterval.FOR_SCHEDULE_HOUR * MILLIS_PER_HOUR

    private fun StandingEntity.dataMayOutdated(): Boolean = System.currentTimeMillis() - this.timeStamp >
            ForceRefreshInterval.FOR_STANDING_HOUR * MILLIS_PER_HOUR

    private fun PlayOffEntity.dataMayOutdated(): Boolean = System.currentTimeMillis() - this.timeStamp >
            ForceRefreshInterval.FOR_STANDING_PLAYOFF * MILLIS_PER_HOUR
}