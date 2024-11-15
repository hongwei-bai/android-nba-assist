package com.mikeapp.sportsmate.data.network.service

import com.mikeapp.sportsmate.data.network.model.soccer.SoccerTeamScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SoccerStatService {
    @GET("teamSchedule.do")
    suspend fun getTeamSchedule(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<SoccerTeamScheduleResponse>
}