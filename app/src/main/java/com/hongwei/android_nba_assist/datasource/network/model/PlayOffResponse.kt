package com.hongwei.android_nba_assist.datasource.network.model

data class PlayOffResponse(
    val dataVersion: Long,
    val seasonOngoing: Boolean,
    val playInOngoing: Boolean,
    val playOffOngoing: Boolean,
    val playIn: PlayInResponse,
    val playOff: PlayOffResponseSub
)

data class PlayInResponse(
    val western: PlayInConferenceResponse,
    val eastern: PlayInConferenceResponse
)

data class PlayOffResponseSub(
    val western: PlayOffConferenceResponse,
    val eastern: PlayOffConferenceResponse,
    val grandFinal: PlayOffGrandFinalSeriesResponse
)

data class PlayInConferenceResponse(
    val winnerOf78: String,
    val winnerOf910: String,
    val lastWinner: String
)

data class PlayOffConferenceResponse(
    val round1: PlayOffRound1Response,
    val round2: PlayOffRound2Response,
    val conferenceFinal: PlayOffSeriesResponse
)

data class PlayOffRound1Response(
    val series18: PlayOffSeriesRound1Response,
    val series45: PlayOffSeriesRound1Response,
    val series36: PlayOffSeriesRound1Response,
    val series27: PlayOffSeriesRound1Response
)

data class PlayOffRound2Response(
    val seriesUpper: PlayOffSeriesResponse,
    val seriesLower: PlayOffSeriesResponse
)

data class PlayOffSeriesRound1Response(
    val scoreHighRank: Int,
    val scoreLowRank: Int,
    val winner: String
)

data class PlayOffSeriesResponse(
    val teamFromUpper: String,
    val teamFromLower: String,
    val scoreUpperWinner: Int,
    val scoreLowerWinner: Int,
    val winner: String
)

data class PlayOffGrandFinalSeriesResponse(
    val teamFromWestern: String,
    val teamFromEastern: String,
    val scoreWesternWinner: Int,
    val scoreEasternWinner: Int,
    val winner: String
)