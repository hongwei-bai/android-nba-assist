package com.hongwei.android_nba_assist.data.network.service

import com.hongwei.android_nba_assist.data.network.model.nba.*
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

    @GET("postSeason.do")
    suspend fun getPostSeason(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<PostSeasonResponse>

    @GET("seasonStatus.do")
    suspend fun getSeasonStatus(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<String>

    @GET("schedule.do")
    suspend fun getSchedule(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<ScheduleResponse>

    @GET("transactions.do")
    suspend fun getTransactions(
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<NbaTransactionsResponse>
}