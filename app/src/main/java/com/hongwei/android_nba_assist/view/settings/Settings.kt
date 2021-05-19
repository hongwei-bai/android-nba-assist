package com.hongwei.android_nba_assist.view.settings

import androidx.compose.foundation.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import com.hongwei.android_nba_assist.view.dashboard.Banner
import com.hongwei.android_nba_assist.viewmodel.SettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Settings() {
    val viewModel = hiltNavGraphViewModel<SettingsViewModel>()
    val displayDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(ScrollState(0))
    ) {
        Banner(viewModel.teamBanner.observeAsState().value)
        if (displayDialog.value) {
            TeamPickerDialog { teamAbbr ->
                displayDialog.value = false
                teamAbbr?.let {
                    viewModel.switchTeam(context, teamAbbr)
                }
            }
        }
        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { displayDialog.value = true }
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .height(24.dp)
        ) {
            Text(text = stringResource(id = R.string.settings_switch_team))
        }

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
        SettingsStartFromMonday(modifier = Modifier.height(24.dp))

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        SettingsScheduleWeeks(modifier = Modifier.height(24.dp))

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
    }
}

@Composable
private fun SettingsStartFromMonday(modifier: Modifier) {
    val viewModel = hiltNavGraphViewModel<SettingsViewModel>()
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Text(text = stringResource(id = R.string.settings_start_from_monday))
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        val checkedState = remember { mutableStateOf(AppSettings.weekStartsFromMonday) }
        Text(text = stringResource(id = R.string.settings_sunday))
        Switch(
            modifier = Modifier.padding(horizontal = 4.dp),
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                viewModel.setWeekStartFromMonday(context, it)
            })
        Text(text = stringResource(id = R.string.settings_monday))
    }
    if (viewModel.startFromMondaySettingChanged.observeAsState().value == true) {
        SettingChangedNeedRestartText()
    }
}

@Composable
private fun SettingsScheduleWeeks(modifier: Modifier) {
    val viewModel = hiltNavGraphViewModel<SettingsViewModel>()
    val context = LocalContext.current
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
        val checkedState = remember { mutableStateOf(AppSettings.scheduleWeeks == 4) }
        Text(text = stringResource(id = R.string.settings_2_weeks))
        Switch(
            modifier = Modifier.padding(horizontal = 4.dp),
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                viewModel.setScheduleWeeks(context, if (it) 4 else 2)
            })
        Text(text = stringResource(id = R.string.settings_4_weeks))
    }
    if (viewModel.scheduleWeeksSettingChanged.observeAsState().value == true) {
        SettingChangedNeedRestartText()
    }
}

@Composable
fun SettingChangedNeedRestartText() {
    Text(
        text = stringResource(id = R.string.settings_changed_need_restart),
        style = MaterialTheme.typography.overline,
        color = MaterialTheme.colors.error,
        overflow = TextOverflow.Visible,
        softWrap = true,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp)
    )
}