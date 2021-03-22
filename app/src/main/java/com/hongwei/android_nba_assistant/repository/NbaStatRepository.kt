package com.hongwei.android_nba_assistant.repository

import com.hongwei.android_nba_assistant.datasource.local.TeamScheduleLocalJsonParser
import com.hongwei.android_nba_assistant.model.TeamSchedule
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val jsonParser: TeamScheduleLocalJsonParser
) {
    suspend fun getTeamSchedule(team: String): TeamSchedule = jsonParser.getTeamSchedule(team)
}