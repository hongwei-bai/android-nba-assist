package com.mikeapp.sportsmate.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.mikeapp.sportsmate.R
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
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onError
            )
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    delay(3000)
                    displayState.value = false
                }
            }
        }
    }
}

sealed class DataStatus {
    object DataMayOutdated : DataStatus()
    object DataIsUpToDate : DataStatus()
    data class ServiceError(val message: String? = null) : DataStatus()
}