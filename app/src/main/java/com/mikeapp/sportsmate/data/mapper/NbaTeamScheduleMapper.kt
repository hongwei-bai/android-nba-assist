package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.mapper.NbaTeamMapper.mapTeam
import com.mikeapp.sportsmate.data.network.model.nba.Event
import com.mikeapp.sportsmate.data.network.model.nba.NbaMatchStatusIdResponse
import com.mikeapp.sportsmate.data.network.model.nba.TeamScheduleResponse
import com.mikeapp.sportsmate.data.room.nba.Team
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity
import com.mikeapp.sportsmate.data.room.nba.TeamEvent
import com.mikeapp.sportsmate.data.room.nba.TeamResult
import com.mikeapp.sportsmate.data.room.nba.TeamScheduleEntity
import com.mikeapp.sportsmate.data.network.model.nba.Result
import com.mikeapp.sportsmate.util.LocalDateTimeUtil

object NbaTeamScheduleMapper {
    fun TeamScheduleResponse.map(teamDetail: TeamDetailEntity): TeamScheduleEntity =
        TeamScheduleEntity(
            teamAbbr = teamDetail.team,
            team = Team(
                abbrev = teamDetail.team,
                name = teamDetail.teamDisplayName,
                logo = teamDetail.logoUrl,
                location = teamDetail.city
            ),
            timeStamp = System.currentTimeMillis(),
            dataVersion = dataVersion,
            sha = null,
            events = (teamSchedule[0].events.pre + teamSchedule[0].events.pre)
                .map { it.map(teamDetail) }
        )

    private fun Event.map(teamDetail: TeamDetailEntity): TeamEvent = TeamEvent(
        unixTimeStamp = LocalDateTimeUtil.utcStringToUnixTimeStamp(date.date),
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
        result = result.map()
    )

    private fun Result.map(): TeamResult? {
        return when (statusId.toInt()) {
            NbaMatchStatusIdResponse.Finished.value -> {
                TeamResult(
                    isWin = winner == true,
                    currentTeamScore = currentTeamScore.toInt(),
                    opponentTeamScore = opponentTeamScore.toInt()
                )
            }

            else -> null
        }
    }
}