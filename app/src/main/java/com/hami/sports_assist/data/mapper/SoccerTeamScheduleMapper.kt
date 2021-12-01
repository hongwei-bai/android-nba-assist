package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.network.model.soccer.SoccerTeamEventResponse
import com.hami.sports_assist.data.network.model.soccer.SoccerTeamScheduleResponse
import com.hami.sports_assist.data.room.soccer.*

object SoccerTeamScheduleMapper {
    fun SoccerTeamScheduleResponse.map(
        myTeam: SoccerTeam = SoccerTeam(
            teamId = teamId,
            abbrev = teamAbbr,
            displayName = teamDisplayName,
            logo = logo,
            location = location
        )
    ): SoccerTeamScheduleEntity = SoccerTeamScheduleEntity(
        teamId = teamId,
        teamAbbr = teamAbbr,
        teamDisplayName = teamDisplayName,
        logo = logo,
        location = location,
        league = league,
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        events = events.map { it.map(myTeam) }
    )

    private fun SoccerTeamEventResponse.map(
        myTeam: SoccerTeam,
        opponent: SoccerTeam = SoccerTeam(
            teamId = this.opponent.teamId,
            abbrev = this.opponent.abbrev,
            displayName = this.opponent.displayName,
            logo = this.opponent.logo,
            location = this.opponent.location
        ),
        homeAwayEnum: SoccerHomeEnum = SoccerHomeEnum.valueOf(this.homeAway)
    ): SoccerTeamEvent {
        return SoccerTeamEvent(
            opponent = opponent,
            homeTeam = when (homeAwayEnum) {
                SoccerHomeEnum.Home -> myTeam
                SoccerHomeEnum.Away -> opponent
                SoccerHomeEnum.Neutral -> myTeam
            },
            guestTeam = when (homeAwayEnum) {
                SoccerHomeEnum.Home -> opponent
                SoccerHomeEnum.Away -> myTeam
                SoccerHomeEnum.Neutral -> opponent
            },
            unixTimeStamp = unixTimeStamp,
            homeAwayEnum = homeAwayEnum,
            completed = completed,
            league = league,
            result = result?.let { SoccerResultEnum.valueOf(it) },
            score = score,
            otScore = otScore,
            penaltyScore = penaltyScore,
            aggregateScore = aggregateScore,
            winner = winner,
            venue = Venue(
                venue = venue?.venue ?: "",
                city = venue?.city,
                country = venue?.country
            )
        )
    }
}