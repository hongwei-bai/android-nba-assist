package com.hongwei.android_nba_assist.view.standing

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.datasource.room.TeamStanding
import com.hongwei.android_nba_assist.view.component.TeamLogo
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0
import com.hongwei.android_nba_assist.viewmodel.StandingViewModel
import java.util.*

@Composable
fun Standing(navController: NavController) {
    val viewModel = hiltNavGraphViewModel<StandingViewModel>()

    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_gs),
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = BlackAlphaA0)
                .verticalScroll(ScrollState(0))
        ) {
            StandingHeader()
            viewModel.standing.observeAsState().value?.standings
                ?.forEach {
                    Team(it)
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
private fun Team(teamStanding: TeamStanding) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(5f)
        ) {
            Text(
                text = teamStanding.rank.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.width(32.dp)
            )
            TeamLogo(
                team = teamStanding.team,
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 4.dp, end = 4.dp)
            )
            Text(
                text = teamStanding.team.name ?: teamStanding.team.abbrev.toUpperCase(Locale.US),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                softWrap = false
            )
        }
        Column(Modifier.weight(1.2f)) {
            Text(
                text = stringResource(id = R.string.standing_win_lose_template, teamStanding.wins, teamStanding.losses),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = teamStanding.gamesBack.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
        Column(Modifier.weight(1f)) {
            Text(
                text = stringResource(
                    id = R.string.standing_streak_template,
                    teamStanding.currentStreak.first, teamStanding.currentStreak.second
                ),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}