package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.hongwei.android_nba_assist.view.component.SolidColorBar
import com.hongwei.android_nba_assist.viewmodel.DashboardViewModel

@Composable
fun Dashboard(
        navHostController: NavHostController
) {
    val viewModel = hiltNavGraphViewModel<DashboardViewModel>()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SolidColorBar(3.5.dp, MaterialTheme.colors.secondary)
//        Banner(viewModel.teamTheme.observeAsState().value?.bannerUrl)
//        SolidColorLoadingBar(3.5.dp, MaterialTheme.colors.secondary, MaterialTheme.colors.background)
        Spacer(modifier = Modifier.size(10.dp))
        GamesLeftView(82)
        Text(text = "Upcoming game on")
        Text(text = "Today!!")
        Button(onClick = {
            viewModel.switchTeam("gs")
        }) {
            Text(text = "Switch to gs")
        }
        Button(onClick = {
            viewModel.switchTeam("lal")
        }) {
            Text(text = "Switch to lal")
        }
        Button(enabled = false, onClick = {
            navHostController.navigate("standing")
        }) {
            Text(text = "Navigate to Standing")
        }

        Button(onClick = {
            viewModel.debugRoom()
        }) {
            Text(text = "debug Room")
        }
    }
}

