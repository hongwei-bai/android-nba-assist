package com.hongwei.android_nba_assist.datasource.network.service

import com.hongwei.android_nba_assist.datasource.network.model.NbaTeamThemeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaThemeService {
    @GET("teamTheme.do")
    suspend fun getTeamTheme(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<NbaTeamThemeResponse>
}