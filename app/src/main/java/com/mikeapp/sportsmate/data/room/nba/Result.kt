package com.mikeapp.sportsmate.data.room.nba

data class Result(
    val isHomeTeamWin: Boolean,
    val homeTeamScore: Int,
    val guestTeamScore: Int
)
