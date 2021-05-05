package com.hongwei.android_nba_assist.datasource.local

import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_BANNER_EXTENSION
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.NBA_BANNER_PATH
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import javax.inject.Inject

class LocalTeamTheme @Inject constructor() {
    fun getTeamTheme(team: String): TeamThemeEntity = when (team) {
        "gs" -> TeamThemeEntity(
            team = team,
            dataVersion = 0,
            bannerUrl = "$NBA_BANNER_PATH$team$DEFAULT_BANNER_EXTENSION",
            colorLight = 0xFFFFFFFF,
            colorHome = 0xFF1C4189,
            colorGuest = 0xFFFCB826
        )
        "lal" -> TeamThemeEntity(
            team = team,
            dataVersion = 0,
            bannerUrl = "$NBA_BANNER_PATH$team$DEFAULT_BANNER_EXTENSION",
            colorLight = 0xFFFFFFFF,
            colorHome = 0xFFFDB827,
            colorGuest = 0xFF542583
        )
        else -> TeamThemeEntity(
            team = team,
            dataVersion = 0,
            bannerUrl = "$NBA_BANNER_PATH$team$DEFAULT_BANNER_EXTENSION",
            colorLight = 0xFFFFFFFF,
            colorHome = 0xFF1C4189,
            colorGuest = 0xFFFCB826
        )
    }
}