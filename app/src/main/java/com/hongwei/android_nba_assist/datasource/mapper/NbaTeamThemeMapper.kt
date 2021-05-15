package com.hongwei.android_nba_assist.datasource.mapper

import com.hongwei.android_nba_assist.datasource.network.model.NbaTeamTheme
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity

object NbaTeamThemeMapper {
    fun NbaTeamTheme.map(): TeamThemeEntity = TeamThemeEntity(
        team = team,
        dataVersion = dataVersion,
        bannerUrl = bannerUrl,
        backgroundUrl = backgroundUrl,
        colorLight = colorLight,
        colorHome = colorHome,
        colorGuest = colorGuest
    )
}