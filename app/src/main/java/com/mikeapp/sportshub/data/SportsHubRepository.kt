package com.mikeapp.sportshub.data

import android.util.Log
import com.mikeapp.sportshub.data.NetworkModule.espnApiService
import com.mikeapp.sportshub.data.epsn.CurlEspnService
import com.mikeapp.sportshub.data.epsn.EspnParser


class SportsHubRepository(
    private val curlEspnService: CurlEspnService,
    private val espnParser: EspnParser
) {
    suspend fun query() {
        val response = espnApiService.getTeamSchedule("gs", 2)
        Log.d("bbbb", "response.isSuccessful: ${response.isSuccessful}")
        if (response.isSuccessful) {
            val jsonString = curlEspnService.getTeamScheduleJson(response.body()?.string() ?: "")
            Log.d("bbbb", "jsonString: $jsonString")
            val teamSchedule = jsonString?.let { espnParser.parse(jsonString) }
            Log.d("bbbb", "teamSchedule: $teamSchedule")
        }
    }
}