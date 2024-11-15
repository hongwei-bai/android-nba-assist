package com.mikeapp.sportsmate.season.playin

data class PlayInStat(
    val teamsAbbr: List<String>,
    val winnerOf78: String,
    val loserOf78: String,
    val winnerOf910: String,
    val loserOf910: String,
    val lastWinner: String
)

