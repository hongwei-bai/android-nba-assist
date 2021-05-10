package com.hongwei.android_nba_assist.datasource.mapper

import com.hongwei.android_nba_assist.datasource.local.FixEndpoint.getTeamLogoUrl
import com.hongwei.android_nba_assist.datasource.network.model.EventResponse
import com.hongwei.android_nba_assist.datasource.network.model.TeamScheduleResponse
import com.hongwei.android_nba_assist.datasource.room.*

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(team: String): TeamScheduleEntity = TeamScheduleEntity(
        team = team,
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        events = events.map { it.map(team) }
    )

    private fun EventResponse.map(team: String): Event = Event(
        unixTimeStamp = unixTimeStamp,
        opponent = Team(
            abbrev = opponent.abbrev,
            logo = getTeamLogoUrl(opponent.abbrev)
        ),
        guestTeam = Team(
            abbrev = if (opponent.home) opponent.abbrev else team,
            logo = getTeamLogoUrl(if (opponent.home) opponent.abbrev else team)
        ),
        homeTeam = Team(
            abbrev = if (opponent.home) team else opponent.abbrev,
            logo = getTeamLogoUrl(if (opponent.home) team else opponent.abbrev)
        ),
        location = opponent.location,
        home = opponent.home,
        result = result?.let {
            Result(
                winLossSymbol = it.winLossSymbol,
                currentTeamScore = it.currentTeamScore,
                opponentTeamScore = it.opponentTeamScore
            )
        }
    )
}