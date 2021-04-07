package com.hongwei.android_nba_assistant.model

data class TeamScheduleResponse(
    val teamSchedule: TeamSchedule
)

data class TeamSchedule(
    val events: List<Event>
)

data class Event(
    val unixTimeStamp: Long,
    val localDisplayTime: String,
    val opponent: Team
)

data class Team(
    val abbrev: String,
    val displayName: String,
    val logo: String,
    val location: String,
    val home: Boolean
)