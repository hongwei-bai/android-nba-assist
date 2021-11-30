package com.hami.sports_assist.data.room.nba

data class Result(
    val isHomeTeamWin: Boolean,
    val homeTeamScore: Int,
    val guestTeamScore: Int
)
