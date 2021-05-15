package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hongwei.android_nba_assist.view.main.DataStatusSnackBar
import com.hongwei.android_nba_assist.viewmodel.DashboardViewModel

@Composable
fun Dashboard() {
    val viewModel = hiltNavGraphViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(ScrollState(0))
        ) {
            DataStatusSnackBar(viewModel.dataStatus.observeAsState().value)

            GamesLeftView(viewModel.gamesLeft.observeAsState().value)
            Spacer(modifier = Modifier.size(20.dp))

            UpcomingGameCountdown(
                viewModel.upcomingGameTime.observeAsState().value,
                viewModel.countdownString.observeAsState().value
            )
            Spacer(modifier = Modifier.size(30.dp))

            UpcomingGameInfo(
                viewModel.myTeam.observeAsState().value,
                viewModel.nextGameInfo.observeAsState().value
            )
            Spacer(modifier = Modifier.size(10.dp))

            Calendar(
                calendarDays = viewModel.calendarDays.observeAsState().value,
                events = viewModel.upcomingGames.observeAsState().value,
                backgroundUrl = viewModel.teamBackground.observeAsState().value
            )
        }
    }
}

