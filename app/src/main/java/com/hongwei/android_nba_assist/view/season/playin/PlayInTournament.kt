package com.hongwei.android_nba_assist.view.season.playin

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
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
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.datasource.league.Tournament.TBD
import com.hongwei.android_nba_assist.view.animation.LoadingContent
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper.NonParticipatorTextColor
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper.PlayInTeamLogoWrapper
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper.getRankByTeamAbbr
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper.getTeamStatus
import com.hongwei.android_nba_assist.view.season.playin.PlayInHelper.getTextColorByTeam
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0
import java.util.*

@Composable
fun PlayInTournament(standing: List<RankedTeamViewObject>?, playInViewObject: PlayInViewObject?, onLeft: Boolean) {
    if (standing != null && playInViewObject != null) {
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
                    .verticalScroll(ScrollState(0))
            ) {
                PlayInHeader(onLeft)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(10f)
                ) {
                    val columnWeight = listOf(3f, 2f, 2f)
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[0])
                    ) {
                        standing.subList(0, 10).forEach {
                            PlayInColumn1(it, playInViewObject, Modifier.weight(1f))
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[1])
                    ) {
                        Spacer(modifier = Modifier.weight(6f))
                        val winnerSymbols = listOf(true, false, true)
                        val hasRound2 = listOf(false, true, true)
                        listOf(playInViewObject.winnerOf78, playInViewObject.loserOf78, playInViewObject.winnerOf910)
                            .forEachIndexed { i, teamAbbr ->
                                PlayInColumn2(
                                    teamAbbr = teamAbbr,
                                    lastRoundWinner = winnerSymbols[i],
                                    withLine = hasRound2[i],
                                    advancedRound2 = playInViewObject.lastWinner != TBD && teamAbbr == playInViewObject.lastWinner,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[2])
                    ) {
                        standing.subList(0, 6).forEach {
                            PlayInColumn3(
                                teamAbbr = it.team.abbrev,
                                rank = it.rank,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        PlayInColumn3(
                            teamAbbr = playInViewObject.winnerOf78,
                            rank = getRankByTeamAbbr(standing, playInViewObject.winnerOf78),
                            participateRound1 = true,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        PlayInColumn3(
                            teamAbbr = playInViewObject.lastWinner,
                            rank = getRankByTeamAbbr(standing, playInViewObject.lastWinner),
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
private fun PlayInColumn1(team: RankedTeamViewObject, playInViewObject: PlayInViewObject, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayInTeam(team = team, playInViewObject = playInViewObject, modifier = Modifier.weight(1.5f))
        val colorOnPrimary = MaterialTheme.colors.onPrimary
        val colorSecondary = MaterialTheme.colors.secondary
        Row(modifier = Modifier.weight(1f)) {
            when (team.rank) {
                7 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, true,
                    playInViewObject.winnerOf78 != TBD && team.team.abbrev == playInViewObject.winnerOf78
                )
                8 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, false,
                    playInViewObject.winnerOf78 != TBD && team.team.abbrev == playInViewObject.winnerOf78
                )
                9 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, true,
                    playInViewObject.winnerOf910 != TBD && team.team.abbrev == playInViewObject.winnerOf910
                )
                10 -> TournamentLinePreAdvance(
                    colorOnPrimary, colorSecondary, false,
                    playInViewObject.winnerOf910 != TBD && team.team.abbrev == playInViewObject.winnerOf910
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
            modifier = Modifier.weight(1f)
        )

        PlayInTeamLogo(
            teamAbbr = teamAbbr,
            modifier = Modifier
                .weight(2f)
        )
        Row(modifier = Modifier.weight(1.5f)) {
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
                modifier = modifier.weight(1f)
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = if (rank > 0) rank.toString() else "",
            style = MaterialTheme.typography.subtitle2,
            color = if (participateRound1) MaterialTheme.colors.onPrimary else NonParticipatorTextColor,
            textAlign = TextAlign.End,
            modifier = Modifier.width(20.dp)
        )
        val teamStatus = if (participateRound1) PlayInTeamStatus.Normal else PlayInTeamStatus.NonParticipate
        PlayInTeamLogoWrapper(teamAbbr, teamStatus)
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
        PlayInTeamLogoWrapper(teamAbbr)
    }
}

@Composable
private fun PlayInTeam(team: RankedTeamViewObject, playInViewObject: PlayInViewObject, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = team.rank.toString(),
            style = MaterialTheme.typography.subtitle2,
            color = getTextColorByTeam(team, playInViewObject),
            textAlign = TextAlign.End,
            modifier = Modifier.width(24.dp)
        )
        PlayInTeamLogoWrapper(team.team.abbrev, getTeamStatus(team, playInViewObject))
        Text(
            text = team.team.abbrev.toUpperCase(Locale.US),
            style = MaterialTheme.typography.subtitle2,
            color = getTextColorByTeam(team, playInViewObject),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
private fun PlayInHeader(onLeft: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(
                    if (onLeft) R.string.season_play_in_tournament_title_western
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