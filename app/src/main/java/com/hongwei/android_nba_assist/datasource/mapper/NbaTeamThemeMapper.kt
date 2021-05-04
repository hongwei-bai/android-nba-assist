package com.hongwei.android_nba_assist.datasource.mapper

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.datasource.network.model.NbaTeamTheme
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.view.theme.LightColors

object NbaTeamThemeMapper {
    fun NbaTeamTheme.map(): TeamThemeEntity = TeamThemeEntity(
        team = team,
        dataVersion = dataVersion,
        bannerUrl = bannerUrl,
        colorLight = colorLight,
        colorHome = colorHome,
        colorGuest = colorGuest
    )

    fun NbaTeamTheme?.mapToColors(): Colors =
        if (this?.colorLight != null) {
            lightColors(
                background = Color(colorLight),
                primary = Color(colorHome!!),
                secondary = Color(colorGuest!!),
                onBackground = Color(colorHome),
                onPrimary = Color(colorLight),
                onSecondary = Color(colorGuest)
            )
        } else {
            LightColors
        }
}