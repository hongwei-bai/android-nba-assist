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
        val colorHomeAdapted = ColorAdapter.adaptHomeColor(colorHome)
        val colorGuestAdapted = ColorAdapter.adaptGuestColor(colorGuest)
        val colorLightAdapted = colorLight
        NbaTheme(
            colors = lightColors(
                primary = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.primary,
                primaryVariant = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.primaryVariant,
                onPrimary = colorLightAdapted?.let { Color(it) } ?: NBALightColors.onPrimary,
                secondary = colorGuestAdapted?.let { Color(it) } ?: NBALightColors.secondary,
                secondaryVariant = colorGuestAdapted?.let { Color(it) } ?: NBALightColors.secondaryVariant,
                onSecondary = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.onSecondary,
                background = colorLightAdapted?.let { Color(it) } ?: NBALightColors.background,
                onBackground = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.onBackground
            ),
            content = content
        )
    } ?: NbaTheme(
        colors = NBALightColors,
        content = content
    )
}