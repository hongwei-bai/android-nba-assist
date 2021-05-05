package com.hongwei.android_nba_assist.repository

import com.google.gson.Gson
import com.hongwei.android_nba_assist.datasource.network.model.HttpCode.DATA_VERSION_UP_TO_DATE
import com.hongwei.android_nba_assist.datasource.network.model.StandingData
import com.hongwei.android_nba_assist.datasource.network.model.TeamSchedule
import com.hongwei.android_nba_assist.datasource.network.service.NbaStatService
import com.hongwei.android_nba_assist.datasource.room.StandingDao
import com.hongwei.android_nba_assist.datasource.room.StandingEntity
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleDao
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleEntity
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val standingDao: StandingDao
) {
    suspend fun getTeamScheduleFromLocal(team: String): TeamSchedule =
        teamScheduleDao.getTeamSchedule()?.let {
            Gson().fromJson(it.data, TeamSchedule::class.java)
        } ?: requestTeamScheduleFromNetwork(team)

    suspend fun getStandingFromLocal(): StandingData =
        standingDao.getStanding()?.let {
            Gson().fromJson(it.data, StandingData::class.java)
        } ?: requestStandingFromNetwork()

    suspend fun requestTeamScheduleFromNetwork(team: String): TeamSchedule {
        val cache = teamScheduleDao.getTeamSchedule()
        val currentDataVersion = cache?.dataVersion ?: -1
        val response = nbaStatService.getTeamSchedule(team, currentDataVersion)
        return if (response.code() == DATA_VERSION_UP_TO_DATE) {
            Gson().fromJson(cache?.data, TeamSchedule::class.java)
        } else {
            response.body()!!.let { teamSchedule ->
                teamScheduleDao.save(
                    TeamScheduleEntity(
                        team = team,
                        timeStamp = System.currentTimeMillis(),
                        dataVersion = teamSchedule.dataVersion,
                        data = Gson().toJson(teamSchedule)
                    )
                )
                teamSchedule
            }
        }
    }

    suspend fun requestStandingFromNetwork(): StandingData {
        val cache = standingDao.getStanding()
        val currentDataVersion = cache?.dataVersion ?: -1
        val response = nbaStatService.getStanding(currentDataVersion)
        return if (response.code() == DATA_VERSION_UP_TO_DATE) {
            Gson().fromJson(cache?.data, StandingData::class.java)
        } else {
            response.body()!!.let { standingData ->
                standingDao.save(
                    StandingEntity(
                        timeStamp = System.currentTimeMillis(),
                        dataVersion = standingData.dataVersion,
                        data = Gson().toJson(standingData)
                    )
                )
                standingData
            }
        }
    }
}