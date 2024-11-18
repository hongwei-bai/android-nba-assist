package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.network.model.nba.NbaTeamDetailResponse
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity

object NbaTeamDetailMapper {
    fun NbaTeamDetailResponse.map(): TeamDetailEntity = TeamDetailEntity(
        team = team,
        teamFullName = teamFullName,
        teamDisplayName = teamDisplayName,
        city = city,
        logoUrl = logo,
        bannerUrl = banner,
        backgroundUrl = background,
        colorLight = teamColorLight,
        colorHome = teamColorHome,
        colorGuest = teamColorGuest
    )
}