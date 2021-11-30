package com.hami.sports_assist.data.network.service

import com.hami.sports_assist.data.network.model.nba.NbaTeamDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaThemeService {
    @GET("teamDetail.do")
    suspend fun getTeamDetail(
        @Query("team") team: String
    ): Response<NbaTeamDetailResponse>
}