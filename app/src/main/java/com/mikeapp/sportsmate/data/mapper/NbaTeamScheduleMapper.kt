package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.mapper.NbaTeamMapper.mapTeam
import com.mikeapp.sportsmate.data.network.model.nba.Event
import com.mikeapp.sportsmate.data.network.model.nba.TeamScheduleResponse
import com.mikeapp.sportsmate.data.room.nba.Team
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity
import com.mikeapp.sportsmate.data.room.nba.TeamEvent
import com.mikeapp.sportsmate.data.room.nba.TeamResult
import com.mikeapp.sportsmate.data.room.nba.TeamScheduleEntity

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(teamDetail: TeamDetailEntity): TeamScheduleEntity =
        TeamScheduleEntity(
            teamAbbr = teamDetail.team,
            team = Team(
                abbrev = teamDetail.team,
                name = TODO(),
                logo = teamDetail.logoUrl,
                location = TODO()
            ),
            timeStamp = System.currentTimeMillis(),
            dataVersion = dataVersion,
            sha = null,
            events = (teamSchedule[0].events.pre + teamSchedule[0].events.pre)
                .map { it.map(teamDetail) }
        )

    private fun Event.map(teamDetail: TeamDetailEntity): TeamEvent = TeamEvent(
        unixTimeStamp = unixTimeStamp,
        eventType = seasonType.name,
        opponent = Team(
            abbrev = opponent.abbrev,
            name = opponent.displayName,
            logo = opponent.logo,
            location = opponent.location
        ),
        guestTeam = if (opponent.homeAwaySymbol == "vs") opponent.mapTeam() else teamDetail.mapTeam(),
        homeTeam = if (opponent.homeAwaySymbol == "vs") teamDetail.mapTeam() else opponent.mapTeam(),
        home = opponent.homeAwaySymbol == "vs",
        result = result?.let {
            TeamResult(
                isWin = it.winner == true,
                currentTeamScore = it.currentTeamScore.toInt(),
                opponentTeamScore = it.opponentTeamScore.toInt()
            )
        }
    )
}