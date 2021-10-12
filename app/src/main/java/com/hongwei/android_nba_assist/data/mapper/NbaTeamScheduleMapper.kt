package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.local.ConstantEndpoint.getTeamLogoUrl
import com.hongwei.android_nba_assist.data.network.model.EventResponse
import com.hongwei.android_nba_assist.data.network.model.TeamScheduleResponse
import com.hongwei.android_nba_assist.data.room.*

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
            name = opponent.displayName,
            logo = getTeamLogoUrl(opponent.abbrev)
        ),
        guestTeam = Team(
            abbrev = if (opponent.home) opponent.abbrev else team,
            name = if (opponent.home) opponent.displayName else null,
            logo = getTeamLogoUrl(if (opponent.home) opponent.abbrev else team)
        ),
        homeTeam = Team(
            abbrev = if (opponent.home) team else opponent.abbrev,
            name = if (opponent.home) team else opponent.displayName,
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