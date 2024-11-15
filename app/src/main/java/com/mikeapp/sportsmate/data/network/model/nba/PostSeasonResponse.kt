package com.mikeapp.sportsmate.data.network.model.nba

data class PostSeasonResponse(
    val dataVersion: Long = 0,
    val currentStage: String = "",
    val westernPlayInEventRound1: List<PlayInEventResponse?> = emptyList(),
    val westernPlayInEventRound2: PlayInEventResponse? = null,
    val easternPlayInEventRound1: List<PlayInEventResponse?> = emptyList(),
    val easternPlayInEventRound2: PlayInEventResponse? = null,
    val westernRound1Series: List<PlayOffSeriesResponse?> = emptyList(),
    val easternRound1Series: List<PlayOffSeriesResponse?> = emptyList(),
    val westernRound2Series: List<PlayOffSeriesResponse?> = emptyList(),
    val easternRound2Series: List<PlayOffSeriesResponse?> = emptyList(),
    val westernConferenceFinal: PlayOffSeriesResponse? = null,
    val easternConferenceFinal: PlayOffSeriesResponse? = null,
    val final: PlayOffSeriesResponse? = null
)

data class PlayOffSeriesResponse(
    val team1: PostSeasonTeamResponse,
    val team2: PostSeasonTeamResponse,
    val team1Wins: Int,
    val team2Wins: Int,
    val events: List<EventResponse?>
)

data class PlayInEventResponse(
    val homeTeam: PostSeasonTeamResponse,
    val guestTeam: PostSeasonTeamResponse,
    val result: ResultResponse?
)

data class PostSeasonTeamResponse(
    val teamAbbr: String,
    val displayName: String,
    val logo: String,
    val location: String,
    val rank: Int,
    val seed: Int,
    val recordSummaryWin: Int,
    val recordSummaryLose: Int,
    var isSurviveToPlayOff: Boolean,
    var isSurvive: Boolean = true
)