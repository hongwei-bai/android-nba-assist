package com.hongwei.android_nba_assist.viewmodel.mapper

import com.hongwei.android_nba_assist.datasource.room.PlayOffConferenceEntity
import com.hongwei.android_nba_assist.datasource.room.PlayOffSeriesEntity
import com.hongwei.android_nba_assist.datasource.room.PlayOffSeriesRound1Entity
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffSeriesViewObject
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffSeriesViewObjectRound1
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffViewObject

object PlayOffViewObjectMapper {
    fun PlayOffConferenceEntity.map() = PlayOffViewObject(
        round1series18 = this.round1.series18.map(),
        round1series45 = this.round1.series45.map(),
        round1series36 = this.round1.series36.map(),
        round1series27 = this.round1.series27.map(),
        round2up = this.round2.seriesUpper.map(),
        round2low = this.round2.seriesLower.map(),
        conferenceFinal = this.conferenceFinal.map()
    )

    fun PlayOffSeriesRound1Entity.map() = PlayOffSeriesViewObjectRound1(
        scoreHighRank = this.scoreHighRank,
        scoreLowRank = this.scoreLowRank,
        winner = this.winner
    )

    fun PlayOffSeriesEntity.map() = PlayOffSeriesViewObject(
        teamFromUpper = this.teamFromUpper,
        teamFromLower = this.teamFromLower,
        scoreUpperWinner = this.scoreUpperWinner,
        scoreLowerWinner = this.scoreLowerWinner,
        winner = this.winner
    )
}