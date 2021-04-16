package com.hongwei.android_nba_assistant.usecase

import android.graphics.drawable.Drawable
import java.util.*

data class MatchEvent(
    val opponentAbbrev: String,
    val opponentLogoUrl: String,
    val opponentLogoPlaceholder: Drawable,
    val isHome: Boolean,
    val location: String,
    var date: Calendar
)