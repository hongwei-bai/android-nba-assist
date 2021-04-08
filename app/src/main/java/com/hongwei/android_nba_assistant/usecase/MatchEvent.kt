package com.hongwei.android_nba_assistant.usecase

import java.util.Calendar

data class MatchEvent(
    val opponent: String,
    val opponentLogo: String,
    val isHome: Boolean,
    var date: Calendar
) {
    val teamShort =
        opponentLogo
            .substring(opponentLogo.lastIndexOf("/") + 1, opponentLogo.length)
            .replace(".png", "")
}