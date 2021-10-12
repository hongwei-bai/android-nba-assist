package com.hongwei.android_nba_assist.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.hongwei.android_nba_assist.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DataStatusSnackBar(dataStatus: DataStatus?) {
    val message: String? = when (dataStatus) {
        is DataStatus.DataMayOutdated -> stringResource(id = R.string.snack_bar_data_may_outdated)
        is DataStatus.DataIsUpToDate -> stringResource(id = R.string.snack_bar_data_up_to_date)
        is DataStatus.ServiceError -> dataStatus.message ?: stringResource(id = R.string.snack_bar_service_error)
        else -> null
    }
    val displayState = remember { mutableStateOf(message != null) }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = displayState.value && message != null,
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        Snackbar {
            Text(
                text = message ?: "",
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onError
            )
            coroutineScope.launch {
                delay(3000)
                displayState.value = false
            }
        }
    }
}

sealed class DataStatus {
    object DataMayOutdated : DataStatus()
    object DataIsUpToDate : DataStatus()
    data class ServiceError(val message: String? = null) : DataStatus()
}