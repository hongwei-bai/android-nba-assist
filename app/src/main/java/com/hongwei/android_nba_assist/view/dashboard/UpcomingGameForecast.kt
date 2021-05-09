package com.hongwei.android_nba_assist.view.dashboard

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getInDays
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getInHours
import java.util.*

@Preview
@Composable
fun UpcomingGameForecast(@PreviewParameter(UrlProvider::class) eventTime: Long?) {
    eventTime?.let {
        Calendar.getInstance().apply { timeInMillis = it }
    }?.run {
        Log.d("bbbb", "getInDays: ${getInDays(this)}")
        when (val inDays = getInDays(this)) {
            0 -> {
                when (val inHours = getInHours(this)) {
                    in 0..2 -> Text(text = stringResource(id = R.string.upcoming_game_in))
                    in 2..8 -> {
                        Text(text = stringResource(id = R.string.upcoming_game_in))
                        Text(text = stringResource(id = R.string.upcoming_game_in_hours, inHours))
                    }
                    in Int.MIN_VALUE until 0 -> Text(text = stringResource(id = R.string.upcoming_game_started))
                    else -> Text(text = stringResource(id = R.string.upcoming_game_on))
                }
            }
            1 -> {
                Text(text = stringResource(id = R.string.upcoming_game_on))
                Text(text = stringResource(id = R.string.upcoming_game_tomorrow))
            }
            in 2..Int.MAX_VALUE -> {
                Text(text = stringResource(id = R.string.upcoming_game_in))
                Text(text = stringResource(id = R.string.upcoming_game_in_days, inDays))
            }
            else -> Placeholder()
        }
    } ?: Placeholder()
}

@Composable
private fun Placeholder() {
    Text(text = "")
    Text(text = "")
}