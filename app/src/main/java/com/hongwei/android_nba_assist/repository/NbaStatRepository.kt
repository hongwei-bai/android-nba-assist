package com.hongwei.android_nba_assist.repository

import com.hongwei.android_nba_assist.datasource.mapper.NbaStandingMapper.map
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamScheduleMapper.map
import com.hongwei.android_nba_assist.datasource.model.DataSourceResult
import com.hongwei.android_nba_assist.datasource.model.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.network.service.NbaStatService
import com.hongwei.android_nba_assist.datasource.room.StandingDao
import com.hongwei.android_nba_assist.datasource.room.StandingEntity
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleDao
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
        private val nbaStatService: NbaStatService,
        private val teamScheduleDao: TeamScheduleDao,
        private val standingDao: StandingDao
) {
    fun getNextGame(team: String): Flow<DataSourceResult<TeamScheduleEntity>> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            DataSourceSuccessResult(it)
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
}