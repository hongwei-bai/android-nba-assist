package com.hongwei.android_nba_assist.dashboard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hongwei.android_nba_assist.ui.animation.ErrorView
import com.hongwei.android_nba_assist.ui.animation.LoadingContent
import com.hongwei.android_nba_assist.ui.component.DataStatus
import com.hongwei.android_nba_assist.ui.component.DataStatusSnackBar

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val dataStatus = viewModel.dataStatus.observeAsState().value
    val seasonStatus = viewModel.seasonStatus.observeAsState().value

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
    ) {
        val gamesLeft = viewModel.gamesLeft.observeAsState().value
        val calendarDays = viewModel.calendarDays.observeAsState().value
        val upcomingGames = viewModel.upcomingGames.observeAsState().value

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            DataStatusSnackBar(viewModel.dataStatus.observeAsState().value)
            if (calendarDays != null
                && gamesLeft != null
                && upcomingGames != null
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(ScrollState(0))
                ) {
                    GamesLeftView(gamesLeft)
                    Spacer(modifier = Modifier.size(20.dp))

                    UpcomingGameCountdown(
                        eventTime = viewModel.upcomingGameTime.observeAsState().value,
                        countdown = viewModel.countdownString.observeAsState().value
                    )
                    Spacer(modifier = Modifier.size(30.dp))

                    UpcomingGameInfo(
                        myTeam = viewModel.myTeam.observeAsState().value,
                        event = viewModel.nextGameInfo.observeAsState().value
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    Calendar(
                        calendarDays = calendarDays,
                        events = upcomingGames,
                        backgroundUrl = viewModel.teamBackground.observeAsState().value
                    )

                    Spacer(modifier = Modifier.size(32.dp))
                    Text(
                        text = seasonStatus?.displayName ?: "",
                        style = MaterialTheme.typography.overline,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                when (dataStatus) {
                    is DataStatus.ServiceError -> ErrorView(dataStatus.message)
                    else -> LoadingContent()
                }
            }
        }
    }
}

