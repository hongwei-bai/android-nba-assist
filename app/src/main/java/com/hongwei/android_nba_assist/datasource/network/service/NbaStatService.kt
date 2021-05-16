package com.hongwei.android_nba_assist.datasource.network.service

import com.hongwei.android_nba_assist.datasource.network.model.PlayOffResponse
import com.hongwei.android_nba_assist.datasource.network.model.StandingResponse
import com.hongwei.android_nba_assist.datasource.network.model.TeamScheduleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaStatService {
    @GET("teamSchedule.do")
    suspend fun getTeamSchedule(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<TeamScheduleResponse>

    @GET("standing.do")
    suspend fun getStanding(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<StandingResponse>

    @GET("playOff.do")
    suspend fun getPlayOff(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<PlayOffResponse>
}