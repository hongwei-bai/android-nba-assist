package com.hongwei.android_nba_assist.view.season

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getResourceIdByName
import com.hongwei.android_nba_assist.view.component.TeamLogo
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0
import com.hongwei.android_nba_assist.view.theme.Grey50
import com.hongwei.android_nba_assist.view.theme.Grey60
import com.hongwei.android_nba_assist.view.theme.Red900
import com.hongwei.android_nba_assist.viewmodel.StandingViewModel
import java.util.*

@Composable
fun Standing() {
    val viewModel = hiltNavGraphViewModel<StandingViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = BlackAlphaA0)
                .verticalScroll(ScrollState(0))
        ) {
            StandingHeader()
            viewModel.standing.observeAsState().value
                ?.forEach {
                    Team(it)
                    if (it.rank == 10) {
                        Divider(color = Red900, thickness = 1.dp)
                    }
                }
        }
    }
}

@Composable
private fun StandingHeader() {
    Row {
        Spacer(Modifier.weight(5f))
        Column(Modifier.weight(1.2f)) {
            Text(
                text = stringResource(id = R.string.standing_header_win_lose),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.standing_header_games_back),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.standing_header_streak),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun Team(rank: RankedTeamViewObject) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .padding(top = 0.dp, bottom = 0.dp)
    ) {
        val color = when (rank.rank) {
            in 1..6 -> MaterialTheme.colors.secondary
            in 7..8 -> MaterialTheme.colors.onPrimary
            in 9..10 -> Grey50
            else -> Grey60
        }
        val iconAlpha = when (rank.rank) {
            in 1..6 -> 1f
            in 7..8 -> 0.9f
            in 9..10 -> 0.7f
            else -> 0.5f
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(5f)
        ) {
            Text(
                text = rank.rank.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.End,
                modifier = Modifier.width(32.dp)
            )
            TeamLogo(
                logoUrl = rank.team.logo,
                localPlaceholderResId = getResourceIdByName(LocalContext.current, rank.team.abbrev),
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 4.dp, end = 4.dp)
                    .alpha(iconAlpha)
            )
            Text(
                text = rank.team.name ?: rank.team.abbrev.toUpperCase(Locale.US),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                softWrap = false
            )
        }
        Column(Modifier.weight(1.2f)) {
            Text(
                text = stringResource(id = R.string.standing_win_lose_template, rank.wins, rank.losses),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = rank.gamesBack.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(
                    id = R.string.standing_streak_template,
                    rank.currentStreak.first, rank.currentStreak.second
                ),
                style = MaterialTheme.typography.subtitle2,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class RankedTeamViewObject(
    val rank: Int,
    val team: TeamViewObject,
    val wins: Int,
    val losses: Int,
    val gamesBack: Double,
    val currentStreak: Pair<String, Int>,
    val last10Records: Pair<Int, Int>
)

data class TeamViewObject(
    val abbrev: String,
    val name: String?,
    val logo: String
)