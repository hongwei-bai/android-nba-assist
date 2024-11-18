package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.network.model.nba.Opponent
import com.mikeapp.sportsmate.data.room.nba.Team
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity

object NbaTeamMapper {
    fun TeamDetailEntity.mapTeam(): Team =
        Team(
            abbrev = team,
            name = teamDisplayName,
            logo = logoUrl,
            location = city
        )

    fun Opponent.mapTeam(): Team =
        Team(
            abbrev = abbrev,
            name = this.displayName,
            logo = this.logo,
            location = location
        )
}