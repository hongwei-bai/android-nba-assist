package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.mapper.NbaTeamMapper.mapTeam
import com.hongwei.android_nba_assist.data.network.model.nba.TeamEventResponse
import com.hongwei.android_nba_assist.data.network.model.nba.TeamResponse
import com.hongwei.android_nba_assist.data.network.model.nba.TeamScheduleResponse
import com.hongwei.android_nba_assist.data.room.nba.Team
import com.hongwei.android_nba_assist.data.room.nba.TeamEvent
import com.hongwei.android_nba_assist.data.room.nba.TeamResult
import com.hongwei.android_nba_assist.data.room.nba.TeamScheduleEntity

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(teamAbbr: String): TeamScheduleEntity = TeamScheduleEntity(
        teamAbbr = teamAbbr,
        team = team.mapTeam(),
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        events = events.map { it.map(team) }
    )

    private fun TeamEventResponse.map(team: TeamResponse): TeamEvent = TeamEvent(
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
            TeamResult(
                isWin = it.win,
                currentTeamScore = it.currentTeamScore,
                opponentTeamScore = it.opponentTeamScore
            )
        }
    )
}