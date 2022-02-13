package com.hami.sports_assist.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hami.sports_assist.ui.animation.ErrorView
import com.hami.sports_assist.ui.animation.LoadingContent
import com.hami.sports_assist.ui.component.DataStatus
import com.hami.sports_assist.ui.component.DataStatusSnackBar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val dataStatus = viewModel.dataStatus.observeAsState().value
    val seasonStatus = viewModel.seasonStatus.observeAsState().value
    var screenSize by remember { mutableStateOf(IntSize.Zero) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
    ) {
        val gamesLeft = viewModel.gamesLeft.observeAsState().value
        val calendarDays = viewModel.calendarDays.observeAsState().value
        val upcomingGames = viewModel.upcomingGames.observeAsState().value

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { screenSize = it }
        ) {
            DataStatusSnackBar(viewModel.dataStatus.observeAsState().value)
            if (calendarDays != null
                && gamesLeft != null
                && upcomingGames != null
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    GamesLeftView(gamesLeft)
                    Spacer(modifier = Modifier.size(20.dp))

                    UpcomingGameCountdown(
                        eventTime = viewModel.upcomingGameTime.observeAsState().value,
                        countdown = viewModel.countdownString.observeAsState().value
                    )
                    Spacer(modifier = Modifier.size(30.dp))

                    UpcomingGameInfo(
                        myTeam = viewModel.myNbaTeam.observeAsState().value,
                        event = viewModel.nextGameInfo.observeAsState().value
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    Calendar(
                        calendarDays = calendarDays,
                        events = upcomingGames,
                        backgroundUrl = viewModel.teamBackground.observeAsState().value,
                        screenSize = screenSize
                    )

                    Spacer(modifier = Modifier.size(32.dp))
                    Text(
                        text = seasonStatus?.displayName ?: "",
                        style = MaterialTheme.typography.overline,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(48.dp))
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

