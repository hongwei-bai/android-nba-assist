package com.hongwei.android_nba_assist.view.season.playoff

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.util.ResourceByNameUtil
import com.hongwei.android_nba_assist.view.animation.LoadingContent
import com.hongwei.android_nba_assist.view.component.TeamLogo
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.playin.AdvancedLineStroke
import com.hongwei.android_nba_assist.view.season.playin.LineStroke
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0
import java.util.*

@Composable
fun PlayOff(standing: List<RankedTeamViewObject>?, playOffViewObject: PlayOffViewObject?, onLeft: Boolean) {
    if (standing != null && playOffViewObject != null) {
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
                    .verticalScroll(ScrollState(0))
            ) {
                PlayOffHeader(onLeft)
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
                            playOffViewObject.round1series18,
                            playOffViewObject.round1series45,
                            playOffViewObject.round1series36,
                            playOffViewObject.round1series27
                        )
                        playOffMatching.forEachIndexed { i, rank ->
                            PlayOffColumn1(
                                team = standing[rank - 1],
                                scoreUpper = seriesDataRound1[i / 2].scoreHighRank,
                                scoreLower = seriesDataRound1[i / 2].scoreLowRank,
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
                            playOffViewObject.round1series18.winner,
                            playOffViewObject.round1series45.winner,
                            playOffViewObject.round1series36.winner,
                            playOffViewObject.round1series27.winner
                        )
                        val seriesDataRound2 = listOf(
                            playOffViewObject.round2up,
                            playOffViewObject.round2low
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
                            teamAbbr = playOffViewObject.round2up.winner,
                            isUpperTeam = true,
                            scoreUpper = playOffViewObject.conferenceFinal.scoreUpperWinner,
                            scoreLower = playOffViewObject.conferenceFinal.scoreLowerWinner,
                            winner = playOffViewObject.conferenceFinal.winner,
                            modifier = Modifier.weight(1f)
                        )
                        PlayOffColumn3(
                            teamAbbr = playOffViewObject.round2low.winner,
                            isUpperTeam = false,
                            scoreUpper = playOffViewObject.conferenceFinal.scoreUpperWinner,
                            scoreLower = playOffViewObject.conferenceFinal.scoreLowerWinner,
                            winner = playOffViewObject.conferenceFinal.winner,
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
                            teamAbbr = playOffViewObject.conferenceFinal.winner,
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
    team: RankedTeamViewObject,
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
        PlayOffTeam(modifier = Modifier.weight(3f), team = team)
        Box(modifier = Modifier.weight(1f)) {
            val isHighRankTeam = when (team.rank) {
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
private fun PlayOffTeam(modifier: Modifier = Modifier, team: RankedTeamViewObject) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = team.rank.toString(),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.End,
            modifier = Modifier.width(24.dp)
        )
        TeamLogo(
            logoUrl = team.team.logo,
            localPlaceholderResId = ResourceByNameUtil.getResourceIdByName(LocalContext.current, team.team.abbrev),
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .size(40.dp)
        )
        Text(
            text = team.team.abbrev.toUpperCase(Locale.US),
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
private fun PlayOffHeader(onLeft: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(
                    if (onLeft) R.string.season_play_off_title_western
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

data class PlayOffViewObject(
    val round1series18: PlayOffSeriesViewObjectRound1,
    val round1series45: PlayOffSeriesViewObjectRound1,
    val round1series36: PlayOffSeriesViewObjectRound1,
    val round1series27: PlayOffSeriesViewObjectRound1,
    val round2up: PlayOffSeriesViewObject,
    val round2low: PlayOffSeriesViewObject,
    val conferenceFinal: PlayOffSeriesViewObject
)

data class PlayOffSeriesViewObjectRound1(
    val scoreHighRank: Int,
    val scoreLowRank: Int,
    val winner: String
)

data class PlayOffSeriesViewObject(
    val teamFromUpper: String,
    val teamFromLower: String,
    val scoreUpperWinner: Int,
    val scoreLowerWinner: Int,
    val winner: String
)

private val playOffMatching = listOf(1, 8, 4, 5, 3, 6, 2, 7)