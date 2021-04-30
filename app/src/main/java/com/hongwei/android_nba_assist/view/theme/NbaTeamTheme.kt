package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NbaTeamTheme(
    team1: String,
    content: @Composable () -> Unit
) {
    when (team1) {
        "gs" -> MaterialTheme(
            colors = GSLightColors,
            typography = NbaTypography,
            content = content
        )
        else -> MaterialTheme(
            colors = LightColors,
            typography = NbaTypography,
            content = content
        )
    }
}