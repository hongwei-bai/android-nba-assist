package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.NbaTeamDetailResponse
import com.hongwei.android_nba_assist.data.network.modelv1.NbaTeamTheme
import com.hongwei.android_nba_assist.data.room.TeamThemeEntity

object NbaTeamThemeMapper {
    fun NbaTeamTheme.mapV1(): TeamThemeEntity = TeamThemeEntity(
        team = team,
        logoUrl = "",
        bannerUrl = bannerUrl,
        backgroundUrl = backgroundUrl,
        colorLight = colorLight,
        colorHome = colorHome,
        colorGuest = colorGuest
    )

    fun NbaTeamDetailResponse.map(): TeamThemeEntity = TeamThemeEntity(
        team = team,
        logoUrl = logo,
        bannerUrl = banner,
        backgroundUrl = background,
        colorLight = 0xFFFFFFFF,
        colorHome = teamColor,
        colorGuest = altColor
    )
}