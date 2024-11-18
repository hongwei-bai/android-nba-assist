package com.mikeapp.sportsmate.data.network.model.nba

data class TeamScheduleResponse(
    val dataVersion: Long = 0,
    val team: TeamResponse,
    val events: List<TeamEventResponse>
)

data class TeamResponse(
    val abbrev: String,
    val displayName: String,
    val logo: String,
    val location: String
)

data class TeamEventResponse(
    val unixTimeStamp: Long,
    val eventType: String,
    val opponent: TeamResponse,
    val result: TeamResultResponse? = null,
    val home: Boolean
)

data class TeamResultResponse(
    val win: Boolean,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
)
