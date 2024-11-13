package com.mikeapp.sportshub.data.epsn

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EspnApiService {
    //https://www.espn.com/nba/team/schedule/_/name/gs/seasontype/2
    @GET("/nba/team/schedule/_/name/{teamAbbr}/seasontype/{seasonType}")
    suspend fun getTeamSchedule(
        @Path("teamAbbr") teamAbbr: String,
        @Path("seasonType") seasonType: Int
    ): Response<ResponseBody>
}