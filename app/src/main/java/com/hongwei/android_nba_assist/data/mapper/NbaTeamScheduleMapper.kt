package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.mapper.NbaTeamMapper.mapTeam
import com.hongwei.android_nba_assist.data.network.model.EventResponse
import com.hongwei.android_nba_assist.data.network.model.TeamResponse
import com.hongwei.android_nba_assist.data.network.model.TeamScheduleResponse
import com.hongwei.android_nba_assist.data.room.Event
import com.hongwei.android_nba_assist.data.room.Result
import com.hongwei.android_nba_assist.data.room.Team
import com.hongwei.android_nba_assist.data.room.TeamScheduleEntity

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(teamAbbr: String): TeamScheduleEntity = TeamScheduleEntity(
        teamAbbr = teamAbbr,
        team = team.mapTeam(),
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        events = events.map { it.map(team) }
    )

    private fun EventResponse.map(team: TeamResponse): Event = Event(
        unixTimeStamp = unixTimeStamp,
        eventType = eventType,
        opponent = Team(
            abbrev = opponent.abbrev,
            name = opponent.displayName,
            logo = opponent.logo,
            location = opponent.location
        ),
        guestTeam = if (home) opponent.mapTeam() else team.mapTeam(),
        homeTeam = if (home) team.mapTeam() else opponent.mapTeam(),
        home = home,
        result = result?.let {
            Result(
                isWin = it.win,
                currentTeamScore = it.currentTeamScore,
                opponentTeamScore = it.opponentTeamScore
            )
        }
    )
}