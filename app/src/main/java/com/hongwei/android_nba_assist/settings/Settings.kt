package com.hongwei.android_nba_assist.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.data.local.AppSettings
import com.hongwei.android_nba_assist.ui.component.Banner
import com.hongwei.android_nba_assist.ui.component.SettingItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Settings() {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val displayDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(ScrollState(0))
    ) {
        Banner(
            url = viewModel.teamBanner.observeAsState().value,
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
        )
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
        SettingItem(
            title = stringResource(id = R.string.settings_schedule_weeks),
            offOption = stringResource(id = R.string.settings_2_weeks),
            onOption = stringResource(id = R.string.settings_4_weeks),
            initialState = AppSettings.scheduleWeeks == 4,
            isNeedRestartAppToTakeEffect = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            viewModel.setScheduleWeeks(context, if (it) 4 else 2)
        }

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
        SettingItem(
            title = stringResource(id = R.string.settings_start_from_monday),
            offOption = stringResource(id = R.string.settings_sunday),
            onOption = stringResource(id = R.string.settings_monday),
            initialState = AppSettings.weekStartsFromMonday,
            isNeedRestartAppToTakeEffect = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            viewModel.setWeekStartFromMonday(context, it)
        }

        Divider(color = MaterialTheme.colors.secondary, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
    }
}