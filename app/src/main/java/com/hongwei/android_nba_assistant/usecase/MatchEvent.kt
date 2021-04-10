package com.hongwei.android_nba_assistant.usecase

import java.util.*

data class MatchEvent(
    val opponentAbbrev: String,
    val teamShort: String = opponentAbbrev.toLowerCase(Locale.US),
    val isHome: Boolean,
    val location: String,
    var date: Calendar
)