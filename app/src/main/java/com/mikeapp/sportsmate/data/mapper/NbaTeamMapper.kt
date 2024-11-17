package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.room.nba.Team
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity

object NbaTeamMapper {
    fun TeamDetailEntity.mapTeam(): Team =
        Team(
            abbrev = team,
            name = TODO(),
            logo = logoUrl,
            location = TODO()
        )
}