package com.hongwei.android_nba_assist.view.season.playin

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.datasource.league.Tournament
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.theme.Grey60
import com.hongwei.android_nba_assist.view.theme.Grey80

object PlayInHelper {
    fun getRankByTeamAbbr(standing: List<RankedTeamViewObject>, teamAbbr: String): Int =
        standing.firstOrNull {
            it.team.abbrev == teamAbbr
        }?.rank ?: 0

    fun isEliminatedByTeam(team: RankedTeamViewObject, playInViewObject: PlayInViewObject) =
        isEliminatedByTeam(team.team.abbrev, team.rank, playInViewObject)

    fun isEliminatedByTeam(teamAbbr: String, rank: Int, playInViewObject: PlayInViewObject) =
        when (rank) {
            in 7..8 -> {
                !(playInViewObject.winnerOf78 == Tournament.TBD || teamAbbr == playInViewObject.winnerOf78
                        || playInViewObject.lastWinner == Tournament.TBD || teamAbbr == playInViewObject.lastWinner)
            }
            in 9..10 -> {
                !(playInViewObject.lastWinner == Tournament.TBD || teamAbbr == playInViewObject.lastWinner)
            }
            else -> false
        }

    @Composable
    fun getTextColorByTeam(team: RankedTeamViewObject, playInViewObject: PlayInViewObject): Color = when (team.rank) {
        in 7..8 -> if (isEliminatedByTeam(team, playInViewObject)) EliminatedTeamTextColor else MaterialTheme.colors.onPrimary
        in 9..10 -> if (isEliminatedByTeam(team, playInViewObject)) EliminatedTeamTextColor else MaterialTheme.colors.onPrimary
        else -> NonParticipatorTextColor
    }

    fun getTeamStatus(team: RankedTeamViewObject, playInViewObject: PlayInViewObject): PlayInTeamStatus = when (team.rank) {
        in 7..8 -> if (isEliminatedByTeam(team, playInViewObject)) PlayInTeamStatus.Eliminated else PlayInTeamStatus.Normal
        in 9..10 -> if (isEliminatedByTeam(team, playInViewObject)) PlayInTeamStatus.Eliminated else PlayInTeamStatus.Normal
        else -> PlayInTeamStatus.NonParticipate
    }



    const val NonParticipatorAlpha = 0.6f
    val NonParticipatorTextColor = Grey60

    const val EliminatedAlpha = 0.4f
    val EliminatedTeamTextColor = Grey80

    const val AdvancedLineStroke = 10f
    const val LineStroke = 5f
}