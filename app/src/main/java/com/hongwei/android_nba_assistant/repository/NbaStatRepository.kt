package com.hongwei.android_nba_assistant.repository

import com.google.gson.Gson
import com.hongwei.android_nba_assistant.datasource.network.model.HttpCode.DATA_VERSION_UP_TO_DATE
import com.hongwei.android_nba_assistant.datasource.network.model.TeamSchedule
import com.hongwei.android_nba_assistant.datasource.network.service.NbaStatService
import com.hongwei.android_nba_assistant.datasource.room.TeamScheduleDao
import com.hongwei.android_nba_assistant.datasource.room.TeamScheduleEntity
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao
) {
    suspend fun getTeamSchedule(team: String): TeamSchedule {
        val cache = teamScheduleDao.getTeamSchedule(team)
        val currentDataVersion = cache?.dataVersion ?: -1
        val response = nbaStatService.getTeamSchedule(team, currentDataVersion)
        return if (response.code() == DATA_VERSION_UP_TO_DATE) {
            Gson().fromJson(cache?.data, TeamSchedule::class.java)
        } else {
            response.body()!!.run {
                teamScheduleDao.insert(
                    TeamScheduleEntity(team, teamSchedule.dataVersion, Gson().toJson(teamSchedule))
                )
                teamSchedule
            }
        }
    }
}