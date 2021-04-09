package com.hongwei.android_nba_assistant.datasource.network.service

import com.hongwei.android_nba_assistant.datasource.network.model.TeamScheduleRequest
import com.hongwei.android_nba_assistant.datasource.network.model.TeamScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NbaStatService {
    @GET("nba/teamSchedule.do")
    suspend fun getTeamSchedule(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<TeamScheduleResponse>

    @POST("nba/teamScheduleCompatible.do")
    suspend fun getTeamScheduleCompatible(@Body request: TeamScheduleRequest): TeamScheduleResponse
}