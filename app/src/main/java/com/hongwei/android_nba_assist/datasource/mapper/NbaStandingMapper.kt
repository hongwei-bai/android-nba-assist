package com.hongwei.android_nba_assist.datasource.mapper

import com.hongwei.android_nba_assist.datasource.network.model.StandingResponse
import com.hongwei.android_nba_assist.datasource.network.model.TeamStandingResponse
import com.hongwei.android_nba_assist.datasource.room.StandingEntity
import com.hongwei.android_nba_assist.datasource.room.TeamStanding

object NbaStandingMapper {
    fun StandingResponse.map(): StandingEntity = StandingEntity(
            timeStamp = System.currentTimeMillis(),
            dataVersion = this.dataVersion,
            western = western.teams.map { it.map() },
            eastern = eastern.teams.map { it.map() },
    )

    private fun TeamStandingResponse.map(): TeamStanding = TeamStanding(
            rank = rank,
            teamAbbr = teamAbbr,
            teamName = teamName,
            wins = wins,
            losses = losses,
            gamesBack = gamesBack,
            currentStreak = currentStreak.toPair(),
            last10Records = last10Records.toPair()
    )
}