package com.hongwei.android_nba_assistant.viewmodel.viewobject

data class UpcomingGame(
    val homeTeamShort: String,
    val guestTeamShort: String,
    val dateString: String,
    val timeString: String,
    val inDays: Int,
    val gamesLeft: Int
)