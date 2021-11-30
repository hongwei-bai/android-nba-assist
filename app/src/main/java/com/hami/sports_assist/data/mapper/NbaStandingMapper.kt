package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.league.nba.Conference
import com.hami.sports_assist.data.network.model.nba.StandingResponse
import com.hami.sports_assist.data.network.model.nba.TeamStandingResponse
import com.hami.sports_assist.data.room.nba.ConferenceStanding
import com.hami.sports_assist.data.room.nba.StandingEntity
import com.hami.sports_assist.data.room.nba.Team
import com.hami.sports_assist.data.room.nba.TeamStanding

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