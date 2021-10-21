package com.hongwei.android_nba_assist.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.data.room.TeamDetailEntity

@Composable
fun NbaTeamTheme(
    teamTheme: TeamDetailEntity?,
    content: @Composable () -> Unit
) {
    teamTheme?.run {
        NbaTheme(
            colors = lightColors(
                primary = colorHome?.let { Color(it) } ?: NBALightColors.primary,
                primaryVariant = colorHome?.let { Color(it) } ?: NBALightColors.primaryVariant,
                onPrimary = colorLight?.let { Color(it) } ?: NBALightColors.onPrimary,
                secondary = colorGuest?.let { Color(it) } ?: NBALightColors.secondary,
                secondaryVariant = colorGuest?.let { Color(it) } ?: NBALightColors.secondaryVariant,
                onSecondary = colorHome?.let { Color(it) } ?: NBALightColors.onSecondary,
                background = colorLight?.let { Color(it) } ?: NBALightColors.background,
                onBackground = colorHome?.let { Color(it) } ?: NBALightColors.onBackground
            ),
            content = content
        )
    } ?: NbaTheme(
        colors = NBALightColors,
        content = content
    )
}