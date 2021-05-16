package com.hongwei.android_nba_assist.datasource.mapper

import com.hongwei.android_nba_assist.datasource.network.model.*
import com.hongwei.android_nba_assist.datasource.room.*

object NbaPlayOffMapper {
    fun PlayOffResponse.map(): PlayOffEntity = PlayOffEntity(
        timeStamp = System.currentTimeMillis(),
        dataVersion = this.dataVersion,
        seasonOngoing = this.seasonOngoing,
        playInOngoing = this.playInOngoing,
        playOffOngoing = this.playOffOngoing,
        playIn = PlayInEntity(
            western = this.playIn.western.map(),
            eastern = this.playIn.eastern.map()
        ),
        playOff = PlayOffSubEntity(
            western = PlayOffConferenceEntity(
                round1 = PlayOffRound1Entity(
                    series18 = this.playOff.western.round1.series18.map(),
                    series45 = this.playOff.western.round1.series45.map(),
                    series36 = this.playOff.western.round1.series36.map(),
                    series27 = this.playOff.western.round1.series27.map()
                ),
                round2 = PlayOffRound2Entity(
                    seriesUpper = this.playOff.western.round2.seriesUpper.map(),
                    seriesLower = this.playOff.western.round2.seriesLower.map(),
                ),
                conferenceFinal = this.playOff.western.conferenceFinal.map()
            ),
            eastern = PlayOffConferenceEntity(
                round1 = PlayOffRound1Entity(
                    series18 = this.playOff.eastern.round1.series18.map(),
                    series45 = this.playOff.eastern.round1.series45.map(),
                    series36 = this.playOff.eastern.round1.series36.map(),
                    series27 = this.playOff.eastern.round1.series27.map()
                ),
                round2 = PlayOffRound2Entity(
                    seriesUpper = this.playOff.eastern.round2.seriesUpper.map(),
                    seriesLower = this.playOff.eastern.round2.seriesLower.map(),
                ),
                conferenceFinal = this.playOff.eastern.conferenceFinal.map()
            ),
            grandFinal = this.playOff.grandFinal.map()
        )
    )

    private fun PlayOffSeriesRound1Response.map() = PlayOffSeriesRound1Entity(
        scoreHighRank = scoreHighRank,
        scoreLowRank = scoreLowRank,
        winner = winner
    )

    private fun PlayOffSeriesResponse.map() = PlayOffSeriesEntity(
        teamFromUpper = teamFromUpper,
        teamFromLower = teamFromLower,
        scoreUpperWinner = scoreUpperWinner,
        scoreLowerWinner = scoreLowerWinner,
        winner = winner
    )

    private fun PlayOffGrandFinalSeriesResponse.map() = PlayOffGrandFinalSeriesEntity(
        teamFromWestern = teamFromWestern,
        teamFromEastern = teamFromEastern,
        scoreWesternWinner = scoreWesternWinner,
        scoreEasternWinner = scoreEasternWinner,
        winner = winner
    )

    private fun PlayInConferenceResponse.map() = PlayInConferenceEntity(
        winnerOf78 = winnerOf78,
        winnerOf910 = winnerOf910,
        lastWinner = lastWinner
    )
}