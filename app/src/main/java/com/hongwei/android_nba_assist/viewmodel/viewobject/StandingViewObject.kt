package com.hongwei.android_nba_assist.viewmodel.viewobject

import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assist.datasource.network.model.TeamStandingData
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import java.util.*

data class TeamStandingViewObject(
    val rank: Int,
    val teamAbbr: String,
    val teamName: String,
    val logoUrl: String,
    val logoPlaceholder: Drawable,
    val wins: Int,
    val losses: Int,
    val pct: Double,
    val gamesBack: Double,
    val homeRecords: Pair<Int, Int>,
    val awayRecords: Pair<Int, Int>,
    val divisionRecords: Pair<Int, Int>,
    val conferenceRecords: Pair<Int, Int>,
    val pointsPerGame: Double,
    val opponentPointsPerGame: Double,
    val avePointsDiff: Double,
    val currentStreak: Pair<String, Int>,
    val last10Records: Pair<Int, Int>
) {
    companion object {
        fun fromResponseModel(
            team: TeamStandingData,
            nbaTeamRepository: NbaTeamRepository
        ) = TeamStandingViewObject(
            rank = team.rank,
            teamAbbr = team.teamAbbr,
            teamName = team.teamName,
            logoUrl = nbaTeamRepository.getTeamLogoUrl(team.teamAbbr.toLowerCase(Locale.ROOT)),
            logoPlaceholder = nbaTeamRepository.getTeamLogoPlaceholder(team.teamAbbr.toLowerCase(Locale.ROOT)),
            wins = team.wins,
            losses = team.losses,
            pct = team.pct,
            gamesBack = team.gamesBack,
            homeRecords = team.homeRecords.toPair(),
            awayRecords = team.awayRecords.toPair(),
            divisionRecords = team.divisionRecords.toPair(),
            conferenceRecords = team.conferenceRecords.toPair(),
            pointsPerGame = team.pointsPerGame,
            opponentPointsPerGame = team.opponentPointsPerGame,
            avePointsDiff = team.avePointsDiff,
            currentStreak = team.currentStreak.toPair(),
            last10Records = team.last10Records.toPair(),
        )
    }
}