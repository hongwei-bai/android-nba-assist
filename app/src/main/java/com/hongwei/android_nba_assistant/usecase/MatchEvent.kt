package com.hongwei.android_nba_assistant.usecase

import java.util.Calendar

data class MatchEvent(
    val opponent: String,
    val isHome: Boolean,
    val date: Calendar
)
