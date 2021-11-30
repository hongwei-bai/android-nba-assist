package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.network.model.nba.TeamResponse
import com.hami.sports_assist.data.room.nba.Team

object NbaTeamMapper {
    fun TeamResponse.mapTeam(): Team =
        Team(
            abbrev = abbrev,
            name = displayName,
            logo = logo,
            location = location
        )
}