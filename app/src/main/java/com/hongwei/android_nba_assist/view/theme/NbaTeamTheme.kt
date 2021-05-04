package com.hongwei.android_nba_assist.view.theme

import android.util.Log
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity

@Composable
fun NbaTeamTheme(
    teamTheme: TeamThemeEntity?,
    content: @Composable () -> Unit
) {
    Log.d("bbbb", "Composable NbaTeamTheme, teamTheme: $teamTheme")
//    when (team) {
//        "gs" -> NbaTheme(
//            colors = GSLightColors,
//            content = content
//        )
//        else -> NbaTheme(
//            colors = LightColors,
//            content = content
//        )
//    }
    teamTheme?.run {
        NbaTheme(
            colors = lightColors(
                primary = Color(colorLight!!),
                secondary = Color(colorHome!!),
                background = Color(colorLight),
                onPrimary = Color(colorHome),
                onSecondary = Color(colorGuest!!)
            ),
            content = content
        )
    } ?: NbaTheme(
        colors = GSLightColors,
        content = content
    )
}