package com.hongwei.android_nba_assist.view.indev

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.hongwei.android_nba_assist.R
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

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp))
        SettingsWeekStart(modifier = Modifier.weight(1f))

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp))
        SettingsScheduleWeeks(modifier = Modifier.weight(1f))

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
        ) {

        }
    }
}

@Composable
private fun SettingsWeekStart(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(text = stringResource(id = R.string.settings_schedule_weeks))
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        val checkedState = remember { mutableStateOf(true) }
        Text(text = stringResource(id = R.string.settings_monday))
        Switch(
            modifier = Modifier.padding(horizontal = 4.dp),
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it })
        Text(text = stringResource(id = R.string.settings_sunday))
    }
}

@Composable
private fun SettingsScheduleWeeks(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(text = stringResource(id = R.string.settings_weeks_start_from_monday))
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        val checkedState = remember { mutableStateOf(true) }
        Text(text = stringResource(id = R.string.settings_2_weeks))
        Switch(
            modifier = Modifier.padding(horizontal = 4.dp),
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it })
        Text(text = stringResource(id = R.string.settings_4_weeks))
    }
}