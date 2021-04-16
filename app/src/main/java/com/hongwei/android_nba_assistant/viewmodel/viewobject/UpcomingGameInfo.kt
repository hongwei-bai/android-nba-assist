package com.hongwei.android_nba_assistant.viewmodel.viewobject

import android.graphics.drawable.Drawable

data class UpcomingGameInfo(
    val homeTeamLogoUrl: String,
    val guestTeamLogoUrl: String,
    val homeTeamLogoPlaceholder: Drawable,
    val guestTeamLogoPlaceholder: Drawable,
    val dateString: String,
    val timeString: String
)