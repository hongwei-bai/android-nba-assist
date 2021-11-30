package com.hongwei.android_nba_assist.data.network.service

import com.hongwei.android_nba_assist.data.network.model.nba.NbaTeamDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaThemeService {
    @GET("teamDetail.do")
    suspend fun getTeamDetail(
        @Query("team") team: String
    ): Response<NbaTeamDetailResponse>
}