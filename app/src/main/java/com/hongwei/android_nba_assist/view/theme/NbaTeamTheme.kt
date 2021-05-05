package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity

@Composable
fun NbaTeamTheme(
    teamTheme: TeamThemeEntity?,
    content: @Composable () -> Unit
) {
    teamTheme?.run {
        NbaTheme(
            colors = lightColors(
                primary = Color(colorHome!!),
                primaryVariant = Color(colorHome),
                onPrimary = Color(colorLight!!),
                secondary = Color(colorGuest!!),
                secondaryVariant = Color(colorGuest),
                onSecondary = Color(colorHome),
                background = Color(colorLight),
                onBackground = Color(colorHome)
            ),
            content = content
        )
    } ?: NbaTheme(
        colors = GSLightColors,
        content = content
    )
}

//    val testColors = lightColors(
//        primary = Color.White,
//        primaryVariant = Color.White,
//        onPrimary = Color.Yellow,
//        secondary = Color.Blue,
//        secondaryVariant = Color.Blue,
//        onSecondary = Color.White,
//        background = Color.Red,
//        onBackground = Color.Green,
//        error = Color.Gray,
//        onError = Color.Red,
//        surface = Color.Green,
//        onSurface = Color.Red
//    )