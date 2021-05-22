package com.hongwei.android_nba_assist.view.season.playin

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hongwei.android_nba_assist.datasource.league.Tournament
import com.hongwei.android_nba_assist.view.theme.Grey60
import com.hongwei.android_nba_assist.view.theme.Grey80

object PlayInHelper {
    fun getRankByTeamAbbr(teamAbbr: String, stat: PlayInStat): Int = stat.teamsAbbr.indexOf(teamAbbr) + 1

    private fun isEliminatedByTeam(rank: Int, teamAbbr: String, stat: PlayInStat) =
        when (rank) {
            in 7..8 -> {
                !(stat.winnerOf78 == Tournament.TBD || teamAbbr == stat.winnerOf78
                        || stat.lastWinner == Tournament.TBD || teamAbbr == stat.lastWinner)
            }
            in 9..10 -> {
                !(stat.lastWinner == Tournament.TBD || teamAbbr == stat.lastWinner)
            }
            else -> false
        }

    @Composable
    fun getTextColorByTeam(rank: Int, teamAbbr: String, stat: PlayInStat): Color = when (rank) {
        in 7..8 -> if (isEliminatedByTeam(rank, teamAbbr, stat)) EliminatedTeamTextColor else MaterialTheme.colors.onPrimary
        in 9..10 -> if (isEliminatedByTeam(rank, teamAbbr, stat)) EliminatedTeamTextColor else MaterialTheme.colors.onPrimary
        else -> NonParticipatorTextColor
    }

    fun getTeamStatus(rank: Int, teamAbbr: String, stat: PlayInStat): SeasonTeamStatus = when (rank) {
        in 7..8 -> if (isEliminatedByTeam(rank, teamAbbr, stat)) SeasonTeamStatus.Eliminated else SeasonTeamStatus.Normal
        in 9..10 -> if (isEliminatedByTeam(rank, teamAbbr, stat)) SeasonTeamStatus.Eliminated else SeasonTeamStatus.Normal
        else -> SeasonTeamStatus.NonParticipate
    }

    const val NonParticipatorAlpha = 0.6f
    val NonParticipatorTextColor = Grey60

    const val EliminatedAlpha = 0.4f
    val EliminatedTeamTextColor = Grey80

    const val AdvancedLineStroke = 10f
    const val LineStroke = 5f
}