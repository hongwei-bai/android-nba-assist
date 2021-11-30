package com.hami.sports_assist.season.playoff

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
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.season.common.SeasonTeamLogoWrapper
import com.hami.sports_assist.season.playin.AdvancedLineStroke
import com.hami.sports_assist.season.playin.LineStroke
import com.hami.sports_assist.ui.theme.BlackAlphaA0
import java.util.*

@Composable
fun PlayOff(stat: PlayOffStat?, isLTR: Boolean) {
    if (stat != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxSize()
                    .background(color = BlackAlphaA0)
                    .verticalScroll(rememberScrollState())
            ) {
                PlayOffHeader(isLTR)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(8f)
                ) {
                    val columnWeight = listOf(4f, 2f, 2f, 0.5f)
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[0])
                    ) {
                        val seriesDataRound1 = listOf(
                            stat.round1series18,
                            stat.round1series45,
                            stat.round1series36,
                            stat.round1series27
                        )
                        playOffMatching.forEachIndexed { i, rank ->
                            PlayOffColumn1(
                                rank = rank,
                                teamAbbr = if (i % 2 == 0) seriesDataRound1[i / 2].teamFromUpper
                                else seriesDataRound1[i / 2].teamFromLower,
                                scoreUpper = seriesDataRound1[i / 2].scoreUpperWinner,
                                scoreLower = seriesDataRound1[i / 2].scoreLowerWinner,
                                winner = seriesDataRound1[i / 2].winner,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Round2
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[1])
                    ) {
                        val round2Teams = listOf(
                            stat.round1series18.winner,
                            stat.round1series45.winner,
                            stat.round1series36.winner,
                            stat.round1series27.winner
                        )
                        val seriesDataRound2 = listOf(
                            stat.round2up,
                            stat.round2low
                        )
                        round2Teams.forEachIndexed { i, teamAbbr ->
                            PlayOffColumn2(
                                teamAbbr = teamAbbr,
                                isUpperTeam = i % 2 == 0,
                                scoreUpper = seriesDataRound2[i / 2].scoreUpperWinner,
                                scoreLower = seriesDataRound2[i / 2].scoreLowerWinner,
                                winner = seriesDataRound2[i / 2].winner,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Conference Final
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[2])
                    ) {
                        PlayOffColumn3(
                            teamAbbr = stat.round2up.winner,
                            isUpperTeam = true,
                            scoreUpper = stat.conferenceFinal.scoreUpperWinner,
                            scoreLower = stat.conferenceFinal.scoreLowerWinner,
                            winner = stat.conferenceFinal.winner,
                            modifier = Modifier.weight(1f)
                        )
                        PlayOffColumn3(
                            teamAbbr = stat.round2low.winner,
                            isUpperTeam = false,
                            scoreUpper = stat.conferenceFinal.scoreUpperWinner,
                            scoreLower = stat.conferenceFinal.scoreLowerWinner,
                            winner = stat.conferenceFinal.winner,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Conference Champion
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(columnWeight[2])
                    ) {
                        PlayOffColumn4(
                            teamAbbr = stat.conferenceFinal.winner,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(3f))
            }
        }
    } else {
        LoadingContent(modifier = Modifier.background(color = MaterialTheme.colors.primary))
    }
}

@Composable
private fun PlayOffColumn1(
    rank: Int,
    teamAbbr: String,
    scoreUpper: Int,
    scoreLower: Int,
    winner: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayOffTeam(modifier = Modifier.weight(3f), rank = rank, teamAbbr = teamAbbr)
        Box(modifier = Modifier.weight(1f)) {
            val isHighRankTeam = when (rank) {
                1, 4, 3, 2 -> true
                else -> false
            }
            TournamentLinePreAdvance(
                lineColor = MaterialTheme.colors.onPrimary,
                colorHighlight = MaterialTheme.colors.secondary,
                onTop = isHighRankTeam
            )
            Column {
                Spacer(modifier = Modifier.weight(0.2f))
                Text(
                    text = getScoreString(scoreUpper, scoreLower, isHighRankTeam),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1.8f))
            }
        }
    }
}

@Composable
private fun PlayOffColumn2(
    teamAbbr: String,
    isUpperTeam: Boolean,
    scoreUpper: Int,
    scoreLower: Int,
    winner: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayOffTeamLogo(modifier = Modifier.weight(3f), teamAbbr = teamAbbr)
        Box(modifier = Modifier.weight(2f)) {
            TournamentLinePreAdvance(
                lineColor = MaterialTheme.colors.onPrimary,
                colorHighlight = MaterialTheme.colors.secondary,
                onTop = isUpperTeam
            )
            Column {
                Spacer(modifier = Modifier.weight(1.4f))
                Text(
                    text = getScoreString(scoreUpper, scoreLower, isUpperTeam),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(2.6f))
            }
        }
    }
}

@Composable
private fun PlayOffColumn3(
    teamAbbr: String,
    isUpperTeam: Boolean,
    scoreUpper: Int,
    scoreLower: Int,
    winner: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayOffTeamLogo(modifier = Modifier.weight(3f), teamAbbr = teamAbbr)
        Box(modifier = Modifier.weight(2f)) {
            TournamentLinePreAdvance(
                lineColor = MaterialTheme.colors.onPrimary,
                colorHighlight = MaterialTheme.colors.secondary,
                onTop = isUpperTeam
            )
            Column {
                Spacer(modifier = Modifier.weight(4f))
                Text(
                    text = getScoreString(scoreUpper, scoreLower, isUpperTeam),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.weight(5f))
            }

        }
    }
}

@Composable
private fun PlayOffColumn4(
    teamAbbr: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        PlayOffTeamLogo(modifier = Modifier.weight(3f), teamAbbr = teamAbbr)
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
private fun PlayOffTeamLogo(teamAbbr: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PlayOffHelper.PlayOffTeamLogoWrapper(teamAbbr)
    }
}

@Composable
private fun PlayOffTeam(modifier: Modifier = Modifier, rank: Int, teamAbbr: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = rank.toString(),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.width(24.dp)
        )
        SeasonTeamLogoWrapper(teamAbbr = teamAbbr)
        Text(
            text = teamAbbr.toUpperCase(Locale.US),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
private fun PlayOffHeader(isLTR: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(
                    if (isLTR) R.string.season_play_off_title_western
                    else R.string.season_play_off_title_eastern
                ),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

private fun getScoreString(upperScore: Int, lowerScore: Int, isUpperTeam: Boolean): String =
    if (upperScore > 0 || lowerScore > 0) {
        if (isUpperTeam) upperScore.toString() else lowerScore.toString()
    } else {
        ""
    }

data class PlayOffStat(
    val round1series18: PlayOffSeriesStat,
    val round1series45: PlayOffSeriesStat,
    val round1series36: PlayOffSeriesStat,
    val round1series27: PlayOffSeriesStat,
    val round2up: PlayOffSeriesStat,
    val round2low: PlayOffSeriesStat,
    val conferenceFinal: PlayOffSeriesStat
)

data class PlayOffSeriesStat(
    val teamFromUpper: String,
    val teamFromLower: String,
    val scoreUpperWinner: Int,
    val scoreLowerWinner: Int,
    val winner: String
)

private val playOffMatching = listOf(1, 8, 4, 5, 3, 6, 2, 7)