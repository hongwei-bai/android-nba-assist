package com.hongwei.android_nba_assist.datasource.mapper

import com.hongwei.android_nba_assist.datasource.network.model.EventResponse
import com.hongwei.android_nba_assist.datasource.network.model.TeamScheduleResponse
import com.hongwei.android_nba_assist.datasource.room.Event
import com.hongwei.android_nba_assist.datasource.room.Result
import com.hongwei.android_nba_assist.datasource.room.Team
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleEntity

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(team: String): TeamScheduleEntity = TeamScheduleEntity(
            team = team,
            timeStamp = System.currentTimeMillis(),
            dataVersion = dataVersion,
            events = events.map { it.map() }
    )

    private fun EventResponse.map(): Event = Event(
            unixTimeStamp = unixTimeStamp,
            opponent = Team(
                    abbrev = opponent.abbrev,
                    location = opponent.location,
                    home = opponent.home
            ),
            result = result?.let {
                Result(
                        winLossSymbol = it.winLossSymbol,
                        currentTeamScore = it.currentTeamScore,
                        opponentTeamScore = it.opponentTeamScore
                )
            }
    )
}