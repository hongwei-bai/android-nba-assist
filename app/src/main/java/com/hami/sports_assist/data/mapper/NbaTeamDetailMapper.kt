package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.network.model.nba.NbaTeamDetailResponse
import com.hami.sports_assist.data.room.nba.TeamDetailEntity

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