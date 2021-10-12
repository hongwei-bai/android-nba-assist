package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.league.nba.Conference
import com.hongwei.android_nba_assist.data.local.ConstantEndpoint.getTeamLogoUrl
import com.hongwei.android_nba_assist.data.network.model.StandingResponse
import com.hongwei.android_nba_assist.data.network.model.TeamStandingResponse
import com.hongwei.android_nba_assist.data.room.ConferenceStanding
import com.hongwei.android_nba_assist.data.room.StandingEntity
import com.hongwei.android_nba_assist.data.room.Team
import com.hongwei.android_nba_assist.data.room.TeamStanding

object NbaStandingMapper {
    fun StandingResponse.map(): StandingEntity = StandingEntity(
        timeStamp = System.currentTimeMillis(),
        dataVersion = this.dataVersion,
        western = ConferenceStanding(Conference.Western.name, western.teams.map { it.map() }),
        eastern = ConferenceStanding(Conference.Eastern.name, eastern.teams.map { it.map() })
    )

    private fun TeamStandingResponse.map(): TeamStanding = TeamStanding(
        rank = rank,
        team = Team(abbrev = teamAbbr, name = teamName, logo = getTeamLogoUrl(teamAbbr)),
        wins = wins,
        losses = losses,
        gamesBack = gamesBack,
        currentStreak = currentStreak.toPair(),
        last10Records = last10Records.toPair()
    )
}