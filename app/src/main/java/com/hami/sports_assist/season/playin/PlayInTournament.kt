package com.hami.sports_assist.season.playin

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hami.sports_assist.R
import com.hami.sports_assist.data.league.Tournament.TBD
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.season.common.SeasonTeamLogoWrapper
import com.hami.sports_assist.season.playin.PlayInHelper.NonParticipatorTextColor
import com.hami.sports_assist.season.playin.PlayInHelper.getRankByTeamAbbr
import com.hami.sports_assist.season.playin.PlayInHelper.getTeamStatus
import com.hami.sports_assist.season.playin.PlayInHelper.getTextColorByTeam
import com.hami.sports_assist.ui.theme.BlackAlphaA0
import java.util.*

val roundWidthWeight = listOf(3f, 2f, 1.5f)

// rank + Logo + Abbr, line
val round1SubWidthWeight = listOf(2f, 1f)

//  line, W + TBD/Logo
val round2SubWidthWeight = listOf(1f, 2f, 1.5f)

// aggr.line, rank + TBD/Logo(20.dp)
val round3SubWidthWeight = listOf(1f, 1f)

@Composable
fun PlayInTournament(stat: PlayInStat?, isLTR: Boolean) {
    if (stat != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = BlackAlphaA0)
                    .verticalScroll(rememberScrollState())
            ) {
                PlayInHeader(isLTR)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(10f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(roundWidthWeight[0])
                    ) {
                        stat.teamsAbbr.forEachIndexed { i, teamAbbr ->
                            PlayInColumn1(i + 1, teamAbbr, stat, Modifier.weight(1f))
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(roundWidthWeight[1])
                    ) {
                        Spacer(modifier = Modifier.weight(6f))
                        val winnerSymbols = listOf(true, false, true)
                        val hasRound2 = listOf(false, true, true)
                        listOf(stat.winnerOf78, stat.loserOf78, stat.winnerOf910)
                            .forEachIndexed { i, teamAbbr ->
                                PlayInColumn2(
                                    teamAbbr = teamAbbr,
                                    lastRoundWinner = winnerSymbols[i],
                                    withLine = hasRound2[i],
                                    advancedRound2 = stat.lastWinner != TBD && teamAbbr == stat.lastWinner,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(roundWidthWeight[2])
                    ) {
                        stat.teamsAbbr.subList(0, 6).forEachIndexed { i, teamAbbr ->
                            PlayInColumn3(
                                teamAbbr = teamAbbr,
                                rank = i + 1,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        PlayInColumn3(
                            teamAbbr = stat.winnerOf78,
                            rank = getRankByTeamAbbr(stat.winnerOf78, stat),
                            participateRound1 = true,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        PlayInColumn3(
                            teamAbbr = stat.lastWinner,
                            rank = getRankByTeamAbbr(stat.lastWinner, stat),
                            participateRound1 = true,
                            participateRound2 = true,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(1.5f))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    } else {
        LoadingContent(modifier = Modifier.background(color = MaterialTheme.colors.primary))
    }
}

@Composable
private fun PlayInColumn1(rank: Int, teamAbbr: String, stat: PlayInStat, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayInTeam(rank, teamAbbr, stat, Modifier.weight(round1SubWidthWeight[0]))
        val colorOnPrimary = MaterialTheme.colors.onPrimary
        val colorSecondary = MaterialTheme.colors.secondary
        Row(modifier = Modifier.weight(round1SubWidthWeight[1])) {
            when (rank) {
                7 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, true,
                    stat.winnerOf78 != TBD && teamAbbr == stat.winnerOf78
                )
                8 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, false,
                    stat.winnerOf78 != TBD && teamAbbr == stat.winnerOf78
                )
                9 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, true,
                    stat.winnerOf910 != TBD && teamAbbr == stat.winnerOf910
                )
                10 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, false,
                    stat.winnerOf910 != TBD && teamAbbr == stat.winnerOf910
                )
                else -> {
                }
            }
        }
    }
}

@Composable
private fun PlayInColumn2(
    teamAbbr: String,
    lastRoundWinner: Boolean,
    withLine: Boolean = false,
    advancedRound2: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        Text(
            text = if (lastRoundWinner) stringResource(id = R.string.season_winner_symbol)
            else stringResource(id = R.string.season_loser_symbol),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(round2SubWidthWeight[0])
        )

        PlayInTeamLogo(
            teamAbbr = teamAbbr,
            modifier = Modifier
                .weight(round2SubWidthWeight[1])
        )
        Row(modifier = Modifier.weight(round2SubWidthWeight[2])) {
            if (withLine) {
                TournamentLinePreAdvance(
                    lineColor = MaterialTheme.colors.onPrimary,
                    colorHighlight = MaterialTheme.colors.secondary,
                    onTop = !lastRoundWinner,
                    advanced = advancedRound2,
                )
            }
        }
    }
}

@Composable
private fun PlayInColumn3(
    teamAbbr: String,
    rank: Int,
    participateRound1: Boolean = false,
    participateRound2: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        if (participateRound2) {
            TournamentLinePostAdvance(
                lineColor = MaterialTheme.colors.onPrimary,
                colorHighlight = MaterialTheme.colors.secondary,
                hasWinner = teamAbbr != TBD,
                modifier = modifier.weight(round3SubWidthWeight[0])
            )
        } else {
            Spacer(modifier = Modifier.weight(round3SubWidthWeight[0]))
        }
        Text(
            text = if (rank > 0) rank.toString() else "",
            style = MaterialTheme.typography.subtitle2,
            color = if (participateRound1) MaterialTheme.colors.onPrimary else NonParticipatorTextColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(round3SubWidthWeight[1])
        )
        val teamStatus = if (participateRound1) SeasonTeamStatus.Normal else SeasonTeamStatus.NonParticipate
        SeasonTeamLogoWrapper(teamAbbr, teamStatus)
    }
}

@Composable
private fun TournamentLinePreAdvance(
    lineColor: Color,
    colorHighlight: Color,
    onTop: Boolean,
    advanced: Boolean = false
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawLine(
            start = Offset(x = 0f, y = canvasHeight / 2),
            end = Offset(x = canvasWidth, y = canvasHeight / 2),
            color = if (advanced) colorHighlight else lineColor,
            strokeWidth = if (advanced) AdvancedLineStroke else LineStroke,
            cap = StrokeCap.Round
        )
        if (onTop) {
            drawLine(
                start = Offset(x = canvasWidth, y = canvasHeight / 2),
                end = Offset(x = canvasWidth, y = canvasHeight),
                color = if (advanced) colorHighlight else lineColor,
                strokeWidth = if (advanced) AdvancedLineStroke else LineStroke,
                cap = StrokeCap.Round
            )
        } else {
            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = canvasWidth, y = canvasHeight / 2),
                color = if (advanced) colorHighlight else lineColor,
                strokeWidth = if (advanced) AdvancedLineStroke else LineStroke,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun TournamentLinePostAdvance(
    lineColor: Color,
    colorHighlight: Color,
    hasWinner: Boolean,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawLine(
            start = Offset(x = 0f, y = canvasHeight / 2),
            end = Offset(x = canvasWidth, y = canvasHeight / 2),
            color = if (hasWinner) colorHighlight else lineColor,
            strokeWidth = if (hasWinner) AdvancedLineStroke else LineStroke,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun PlayInTeamLogo(teamAbbr: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        SeasonTeamLogoWrapper(teamAbbr)
    }
}

@Composable
private fun PlayInTeam(rank: Int, teamAbbr: String, stat: PlayInStat, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = rank.toString(),
            style = MaterialTheme.typography.subtitle2,
            color = getTextColorByTeam(rank, teamAbbr, stat),
            textAlign = TextAlign.End,
            modifier = Modifier.width(24.dp)
        )
        SeasonTeamLogoWrapper(teamAbbr, getTeamStatus(rank, teamAbbr, stat))
        Text(
            text = teamAbbr.toUpperCase(Locale.US),
            style = MaterialTheme.typography.subtitle2,
            color = getTextColorByTeam(rank, teamAbbr, stat),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
private fun PlayInHeader(isLTR: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(
                    if (isLTR) R.string.season_play_in_tournament_title_western
                    else R.string.season_play_in_tournament_title_eastern
                ),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

const val AdvancedLineStroke = 10f
const val LineStroke = 5f