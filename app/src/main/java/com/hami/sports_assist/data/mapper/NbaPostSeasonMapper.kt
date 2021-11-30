package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.network.model.nba.*
import com.hami.sports_assist.data.room.nba.*

object NbaPostSeasonMapper {
    fun PostSeasonResponse.map(): PostSeasonEntity = PostSeasonEntity(
        timeStamp = System.currentTimeMillis(),
        dataVersion = dataVersion,
        currentStage = currentStage,
        westernPlayInEventRound1 = westernPlayInEventRound1.map { it?.map() },
        westernPlayInEventRound2 = westernPlayInEventRound2?.map(),
        easternPlayInEventRound1 = easternPlayInEventRound1.map { it?.map() },
        easternPlayInEventRound2 = easternPlayInEventRound2?.map(),
        westernRound1Series = westernRound1Series.map { it?.map() },
        easternRound1Series = easternRound1Series.map { it?.map() },
        westernRound2Series = westernRound2Series.map { it?.map() },
        easternRound2Series = easternRound2Series.map { it?.map() },
        westernConferenceFinal = westernConferenceFinal?.map(),
        easternConferenceFinal = easternConferenceFinal?.map(),
        final = final?.map()
    )

    private fun PlayInEventResponse.map() = PlayInEventEntity(
        homeTeam = homeTeam.map(),
        guestTeam = guestTeam.map(),
        result = result?.map()
    )

    private fun PlayOffSeriesResponse.map() = PlayOffSeriesEntity(
        team1 = team1.map(),
        team2 = team2.map(),
        team1Wins = team1Wins,
        team2Wins = team2Wins,
        events = events.map { it?.map() }
    )

    private fun PostSeasonTeamResponse.map() = PostSeasonTeamEntity(
        teamAbbr = teamAbbr,
        displayName = displayName,
        logo = logo,
        location = location,
        rank = rank,
        seed = seed,
        recordSummaryWin = recordSummaryWin,
        recordSummaryLose = recordSummaryLose,
        isSurviveToPlayOff = isSurviveToPlayOff,
        isSurvive = isSurvive,
    )

    private fun ResultResponse.map() = Result(
        isHomeTeamWin = isHomeTeamWin,
        homeTeamScore = homeTeamScore,
        guestTeamScore = guestTeamScore
    )

    private fun EventResponse.map() = EventEntity(
        unixTimeStamp = unixTimeStamp,
        eventType = eventType,
        homeTeam = homeTeam.map(),
        guestTeam = guestTeam.map(),
        result = result?.map(),
    )

    private fun TeamResponse.map() = Team(
        abbrev = abbrev,
        name = displayName,
        logo = logo,
        location = location,
    )
}