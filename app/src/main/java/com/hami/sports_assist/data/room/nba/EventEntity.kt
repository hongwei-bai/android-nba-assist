package com.hami.sports_assist.data.room.nba

data class EventEntity(
    val unixTimeStamp: Long,
    val eventType: String,
    val homeTeam: Team,
    val guestTeam: Team,
    val result: Result? = null
)
