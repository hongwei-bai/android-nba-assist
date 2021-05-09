package com.hongwei.android_nba_assist.repository

import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.datasource.DataSourceEmptyResult
import com.hongwei.android_nba_assist.datasource.DataSourceResult
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.mapper.NbaStandingMapper.map
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamScheduleMapper.map
import com.hongwei.android_nba_assist.datasource.network.service.NbaStatService
import com.hongwei.android_nba_assist.datasource.room.*
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val standingDao: StandingDao
) {
    fun getNextGameInfo(team: String): Flow<DataSourceResult<Event>> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            it.events.firstOrNull { eventTime ->
                after(eventTime.unixTimeStamp)
            }?.let { event ->
                DataSourceSuccessResult(event)
            } ?: DataSourceEmptyResult()
        }
    }

    fun getTeamSchedule(team: String): Flow<DataSourceResult<TeamScheduleEntity>> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            DataSourceSuccessResult(it)
        }
    }

    fun getStanding(team: String): Flow<DataSourceResult<StandingEntity>> {
        return standingDao.getStanding().onEach {
            it ?: fetchStandingFromBackend(team)
        }.filterNotNull().map {
            DataSourceSuccessResult(it)
        }
    }

    suspend fun fetchTeamScheduleFromBackend(team: String) {
        val response = nbaStatService.getTeamSchedule(team, -1)
        val data = response.body()
        if (response.isSuccessful && data != null) {
            teamScheduleDao.save(data.map(team))
        }
    }

    suspend fun fetchStandingFromBackend(team: String) {
        val response = nbaStatService.getStanding(-1)
        val data = response.body()
        if (response.isSuccessful && data != null) {
            standingDao.save(data.map())
        }
    }

    private fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
}