package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.hongwei.android_nba_assist.compat.view.NbaTeamTheme

@Composable
fun NbaTheme(
    teamTheme: NbaTeamTheme,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColors,
        typography = NbaTypography,
        content = content
    )
}