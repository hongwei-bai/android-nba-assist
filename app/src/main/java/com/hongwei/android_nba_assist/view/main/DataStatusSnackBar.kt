package com.hongwei.android_nba_assist.view.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hongwei.android_nba_assist.R

@Composable
fun DataStatusSnackBar(dataStatus: DataStatus?) {
    val message: String? = when (dataStatus) {
        is DataStatus.DataMayOutdated -> stringResource(id = R.string.snack_bar_data_may_outdated)
        is DataStatus.DataIsUpToDate -> stringResource(id = R.string.snack_bar_data_up_to_date)
        is DataStatus.ServiceError -> stringResource(id = R.string.snack_bar_service_error)
        else -> null
    }
    message?.let {
        Snackbar {
            Text(
                text = message,
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onError
            )
        }
    }
}

sealed class DataStatus {
    object DataMayOutdated : DataStatus()
    object DataIsUpToDate : DataStatus()
    object ServiceError : DataStatus()
}