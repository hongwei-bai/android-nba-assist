package com.hongwei.android_nba_assist.data.network.model

data class TeamScheduleResponse(
        val dataVersion: Long = 0,
        val events: List<EventResponse>
)

data class EventResponse(
        val unixTimeStamp: Long,
        val localDisplayTime: String,
        val opponent: TeamResponse,
        val result: ResultResponse? = null
)

data class TeamResponse(
        val abbrev: String,
        val displayName: String,
        val logo: String,
        val location: String,
        val home: Boolean
)

data class ResultResponse(
        val winLossSymbol: String,
        val currentTeamScore: Int,
        val opponentTeamScore: Int
)