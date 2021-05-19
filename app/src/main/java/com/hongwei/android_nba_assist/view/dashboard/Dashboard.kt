package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hongwei.android_nba_assist.view.animation.LoadingContent
import com.hongwei.android_nba_assist.view.component.DataStatusSnackBar
import com.hongwei.android_nba_assist.viewmodel.DashboardViewModel

@Composable
fun Dashboard() {
    val viewModel = hiltNavGraphViewModel<DashboardViewModel>()

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
                }
            } else {
                LoadingContent()
            }
        }
    }
}

