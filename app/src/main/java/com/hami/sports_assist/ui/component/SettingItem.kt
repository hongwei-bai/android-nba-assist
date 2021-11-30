package com.hami.sports_assist.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hami.sports_assist.R

@Composable
fun SettingItem(
    title: String? = null,
    offOption: String? = null,
    onOption: String? = null,
    initialState: Boolean? = false,
    isNeedRestartAppToTakeEffect: Boolean = true,
    modifier: Modifier = Modifier,
    onChange: (Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(initialState) }
    Column(modifier = modifier) {
        title?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = title)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            offOption?.let { Text(text = it) }
            Switch(
                modifier = Modifier.padding(horizontal = 4.dp),
                checked = checkedState.value ?: false,
                onCheckedChange = {
                    checkedState.value = it
                    onChange.invoke(it)
                })
            onOption?.let { Text(text = it) }
        }
        if (isNeedRestartAppToTakeEffect && initialState != checkedState.value) {
            SettingChangedNeedRestartText()
        }
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
        modifier = Modifier.fillMaxWidth()
    )
}