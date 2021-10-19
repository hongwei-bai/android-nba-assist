package com.hongwei.android_nba_assist.data.network.service

import com.hongwei.android_nba_assist.data.network.model.NbaTeamDetailResponse
import com.hongwei.android_nba_assist.data.network.modelv1.NbaTeamTheme
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaThemeService {
    @Deprecated("NBA V2 use getTeamDetail instead.")
    @GET("teamTheme.do")
    suspend fun getTeamTheme(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<NbaTeamTheme>

    @GET("teamDetail.do")
    suspend fun getTeamDetail(
        @Query("team") team: String
    ): Response<NbaTeamDetailResponse>
}