package com.hongwei.android_nba_assist.viewmodel.mapper

import com.hongwei.android_nba_assist.datasource.room.*
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffSeriesStat
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffStat
import java.util.*

object PlayOffViewObjectMapper {
    fun PlayOffConferenceEntity.map(conferenceStanding: ConferenceStanding, playInConferenceEntity: PlayInConferenceEntity) = PlayOffStat(
        round1series18 = this.round1.series18.map(conferenceStanding.standings[0].team.abbrev, playInConferenceEntity.lastWinner),
        round1series45 = this.round1.series45.map(conferenceStanding.standings[3].team.abbrev, conferenceStanding.standings[4].team.abbrev),
        round1series36 = this.round1.series36.map(conferenceStanding.standings[2].team.abbrev, conferenceStanding.standings[5].team.abbrev),
        round1series27 = this.round1.series27.map(conferenceStanding.standings[1].team.abbrev, playInConferenceEntity.winnerOf78),
        round2up = this.round2.seriesUpper.map(),
        round2low = this.round2.seriesLower.map(),
        conferenceFinal = this.conferenceFinal.map()
    )

    fun PlayOffSeriesRound1Entity.map(upperTeam: String, LowerTeam: String) = PlayOffSeriesStat(
        teamFromUpper = upperTeam.toLowerCase(Locale.US),
        teamFromLower = LowerTeam.toLowerCase(Locale.US),
        scoreUpperWinner = this.scoreHighRank,
        scoreLowerWinner = this.scoreLowRank,
        winner = this.winner
    )

    fun PlayOffSeriesEntity.map() = PlayOffSeriesStat(
        teamFromUpper = this.teamFromUpper,
        teamFromLower = this.teamFromLower,
        scoreUpperWinner = this.scoreUpperWinner,
        scoreLowerWinner = this.scoreLowerWinner,
        winner = this.winner
    )
}