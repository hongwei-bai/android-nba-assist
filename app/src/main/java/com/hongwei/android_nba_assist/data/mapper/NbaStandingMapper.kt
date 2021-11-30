package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.league.nba.Conference
import com.hongwei.android_nba_assist.data.network.model.nba.StandingResponse
import com.hongwei.android_nba_assist.data.network.model.nba.TeamStandingResponse
import com.hongwei.android_nba_assist.data.room.nba.ConferenceStanding
import com.hongwei.android_nba_assist.data.room.nba.StandingEntity
import com.hongwei.android_nba_assist.data.room.nba.Team
import com.hongwei.android_nba_assist.data.room.nba.TeamStanding

object NbaStandingMapper {
    fun StandingResponse.map(): StandingEntity = StandingEntity(
        timeStamp = System.currentTimeMillis(),
        dataVersion = this.dataVersion,
        western = ConferenceStanding(Conference.Western.name, western.map { it.map() }),
        eastern = ConferenceStanding(Conference.Eastern.name, eastern.map { it.map() })
    )

    private fun TeamStandingResponse.map(): TeamStanding = TeamStanding(
        rank = rank,
        team = Team(teamAbbr, teamName, teamLogo, teamLocation),
        wins = wins,
        losses = losses,
        gamesBack = gamesBack,
        currentStreak = currentStreak.toPair(),
        last10Records = last10Records.toPair()
    )
}