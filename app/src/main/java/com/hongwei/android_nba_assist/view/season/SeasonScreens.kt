package com.hongwei.android_nba_assist.view.season

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class SeasonScreens(val icon: ImageVector) {
    object WestStanding : SeasonScreens(Icons.Outlined.FormatListNumbered)
    object WestPlayIn : SeasonScreens(Icons.Outlined.FastForward)
    object WestPlayOff : SeasonScreens(Icons.Outlined.Mediation)
    object Final : SeasonScreens(Icons.Outlined.EmojiEvents)
    object EastPlayOff : SeasonScreens(Icons.Outlined.Mediation)
    object EastPlayIn : SeasonScreens(Icons.Outlined.FastRewind)
    object EastStanding : SeasonScreens(Icons.Outlined.FormatListNumbered)
}