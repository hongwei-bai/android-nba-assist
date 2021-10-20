package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.TeamResponse
import com.hongwei.android_nba_assist.data.room.Team

object NbaTeamMapper {
    fun TeamResponse.mapTeam(): Team =
        Team(
            abbrev = abbrev,
            name = displayName,
            logo = logo,
            location = location
        )
}