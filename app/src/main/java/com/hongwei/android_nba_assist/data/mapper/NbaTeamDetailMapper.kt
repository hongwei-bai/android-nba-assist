package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.nba.NbaTeamDetailResponse
import com.hongwei.android_nba_assist.data.room.nba.TeamDetailEntity

object NbaTeamDetailMapper {
    fun NbaTeamDetailResponse.map(): TeamDetailEntity = TeamDetailEntity(
        team = team,
        logoUrl = logo,
        bannerUrl = banner,
        backgroundUrl = background,
        colorLight = 0xFFFFFFFF,
        colorHome = teamColor,
        colorGuest = altColor
    )
}