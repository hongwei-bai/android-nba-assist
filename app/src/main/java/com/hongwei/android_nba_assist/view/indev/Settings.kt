package com.hongwei.android_nba_assist.view.indev

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.hongwei.android_nba_assist.view.dashboard.Banner
import com.hongwei.android_nba_assist.viewmodel.SettingsViewModel

@Composable
fun Settings() {
    val viewModel = hiltNavGraphViewModel<SettingsViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Banner(viewModel.teamBanner.observeAsState().value)
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    viewModel.switchTeam(context, "gs")
                }
                .padding(start = 12.dp)
        ) {
            Text(text = "Switch to Warriors")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {
                    viewModel.switchTeam(context, "lal")
                }
                .padding(start = 12.dp)
        ) {
            Text(text = "Switch to Lakers")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(12f)
        ) {

        }
    }
}