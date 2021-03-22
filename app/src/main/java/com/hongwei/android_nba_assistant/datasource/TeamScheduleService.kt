package com.hongwei.android_nba_assistant.datasource

import com.hongwei.android_nba_assistant.model.TeamSchedule

interface TeamScheduleService {
    suspend fun getTeamSchedule(team: String): TeamSchedule
}