package com.hongwei.android_nba_assist.view.theme

import android.util.Log
import androidx.compose.runtime.Composable

@Composable
fun NbaTeamTheme(
    team: String?,
    content: @Composable () -> Unit
) {
    Log.d("bbbb", "Composable NbaTeamTheme, team: $team")
    when (team) {
        "gs" -> NbaTheme(
            colors = GSLightColors,
            content = content
        )
        else -> NbaTheme(
            colors = LightColors,
            content = content
        )
    }
}