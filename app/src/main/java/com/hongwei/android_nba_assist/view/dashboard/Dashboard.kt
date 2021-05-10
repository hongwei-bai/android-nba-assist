package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.view.component.SolidColorBar
import com.hongwei.android_nba_assist.viewmodel.DashboardViewModel

@Composable
fun Dashboard(
    navHostController: NavHostController
) {
    val viewModel = hiltNavGraphViewModel<DashboardViewModel>()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SolidColorBar(3.5.dp, MaterialTheme.colors.secondary)

        Banner(viewModel.teamTheme.observeAsState().value?.bannerUrl)
        Spacer(modifier = Modifier.size(10.dp))

        GamesLeftView(viewModel.gamesLeft.observeAsState().value)
        Spacer(modifier = Modifier.size(20.dp))

        UpcomingGameCountdown(
            viewModel.upcomingGameTime.observeAsState().value,
            viewModel.countdownString.observeAsState().value
        )
        Spacer(modifier = Modifier.size(30.dp))

        UpcomingGameInfo(
            viewModel.myTeam.observeAsState().value,
            (viewModel.nextGameInfo.observeAsState().value as? DataSourceSuccessResult)?.data
        )
        Spacer(modifier = Modifier.size(10.dp))

        Calendar(
            viewModel.calendarDays.observeAsState().value,
            viewModel.upcomingGames.observeAsState().value
        )
    }
}

