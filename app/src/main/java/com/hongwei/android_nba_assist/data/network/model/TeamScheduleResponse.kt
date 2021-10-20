package com.hongwei.android_nba_assist.data.network.model

data class TeamScheduleResponse(
    val dataVersion: Long = 0,
    val team: TeamResponse,
    val events: List<EventResponse>
)

data class TeamResponse(
    val abbrev: String,
    val displayName: String,
    val logo: String,
    val location: String
)

data class EventResponse(
    val unixTimeStamp: Long,
    val eventType: String,
    val opponent: TeamResponse,
    val result: ResultResponse? = null,
    val home: Boolean
)

data class ResultResponse(
    val win: Boolean,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
)