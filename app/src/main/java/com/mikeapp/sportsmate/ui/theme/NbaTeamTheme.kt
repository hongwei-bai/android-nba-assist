package com.mikeapp.sportsmate.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity

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
            colors = lightColorScheme(
                primary = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.primary,
                primaryContainer = colorHomeAdapted?.let { Color(it) } ?: NBALightColors.primaryVariant,
                onPrimary = colorLightAdapted?.let { Color(it) } ?: NBALightColors.onPrimary,
                secondary = colorGuestAdapted?.let { Color(it) } ?: NBALightColors.secondary,
                secondaryContainer = colorGuestAdapted?.let { Color(it) } ?: NBALightColors.secondaryVariant,
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