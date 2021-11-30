package com.hongwei.android_nba_assist.data.room.nba

data class EventEntity(
    val unixTimeStamp: Long,
    val eventType: String,
    val homeTeam: Team,
    val guestTeam: Team,
    val result: Result? = null
)
