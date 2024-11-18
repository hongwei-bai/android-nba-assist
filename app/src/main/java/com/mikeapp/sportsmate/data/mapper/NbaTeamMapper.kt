package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.network.model.nba.TeamResponse
import com.mikeapp.sportsmate.data.room.nba.Team

object NbaTeamMapper {
    fun TeamResponse.mapTeam(): Team =
        Team(
            abbrev = abbrev,
            name = displayName,
            logo = logo,
            location = location
        )
}