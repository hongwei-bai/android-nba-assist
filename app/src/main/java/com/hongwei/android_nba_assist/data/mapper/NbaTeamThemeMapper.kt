package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.NbaTeamTheme
import com.hongwei.android_nba_assist.data.room.TeamThemeEntity

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