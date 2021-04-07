package com.hongwei.android_nba_assistant.datasource.network.service

import com.hongwei.android_nba_assistant.datasource.TeamScheduleService
import com.hongwei.android_nba_assistant.model.TeamSchedule
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaStatService : TeamScheduleService {
    @GET("/mock.do")
    override suspend fun getTeamSchedule(@Query(value = "team") team: String): TeamSchedule
}