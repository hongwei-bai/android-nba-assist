package com.mikeapp.sportsmate.data.network.model.nba

data class ScheduleResponse(
    val dataVersion: Long = 0,
    val events: List<EventResponse>
)

data class EventResponse(
    val unixTimeStamp: Long,
    val eventType: String,
    val homeTeam: TeamResponse,
    val guestTeam: TeamResponse,
    val result: ResultResponse? = null
)

data class ResultResponse(
    val isHomeTeamWin: Boolean,
    val homeTeamScore: Int,
    val guestTeamScore: Int
)

data class TeamResponse(
    val abbrev: String,
    val displayName: String,
    val logo: String,
    val location: String
)