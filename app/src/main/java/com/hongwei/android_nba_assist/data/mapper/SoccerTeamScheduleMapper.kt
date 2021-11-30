package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.soccer.SoccerTeamEventResponse
import com.hongwei.android_nba_assist.data.network.model.soccer.SoccerTeamScheduleResponse
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeam
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamEvent
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamScheduleEntity
import com.hongwei.android_nba_assist.data.room.soccer.Venue

object SoccerTeamScheduleMapper {
    fun SoccerTeamScheduleResponse.map(): SoccerTeamScheduleEntity = SoccerTeamScheduleEntity(
        teamId = teamId,
        teamAbbr = teamAbbr,
        teamDisplayName = teamDisplayName,
        logo = logo,
        location = location,
        league = league,
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        events = events.map { it.map() }
    )

    private fun SoccerTeamEventResponse.map(): SoccerTeamEvent = SoccerTeamEvent(
        opponent = SoccerTeam(
            teamId = opponent.teamId,
            abbrev = opponent.abbrev,
            displayName = opponent.displayName,
            logo = opponent.logo,
            location = opponent.location
        ),
        unixTimeStamp = unixTimeStamp,
        homeAway = homeAway,
        completed = completed,
        league = league,
        result = result,
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