package com.hongwei.android_nba_assist.data.network.model

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