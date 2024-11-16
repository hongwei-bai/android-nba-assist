package com.mikeapp.sportsmate.dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikeapp.sportsmate.R
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getInDays
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getInHours
import com.mikeapp.sportsmate.dashboard.CountdownHelper.getUpcomingRange
import java.util.*

@Composable
fun UpcomingGameCountdown(
    eventTime: Long?,
    countdown: String?
) {
    eventTime?.let {
        val calendar = Calendar.getInstance().apply { timeInMillis = it }
        when (val inDays = getInDays(calendar)) {
            0 -> {
                when (getUpcomingRange(it)) {
                    UpcomingRange.InHours -> ForecastContent(
                        stringResource(id = R.string.upcoming_game_in),
                        stringResource(id = R.string.upcoming_game_in_hours, getInHours(calendar))
                    )
                    UpcomingRange.CountingDown, UpcomingRange.Now -> ForecastContent(
                        stringResource(id = R.string.upcoming_game_in),
                        countdown ?: ""
                    )
                    UpcomingRange.Started, UpcomingRange.CountingUp -> ForecastContent(
                        stringResource(id = R.string.upcoming_game_started),
                        countdown ?: "", false
                    )
                    else -> ForecastContent(
                        stringResource(id = R.string.upcoming_game_on),
                        stringResource(id = R.string.upcoming_game_today)
                    )
                }
            }
            1 -> ForecastContent(
                stringResource(id = R.string.upcoming_game_on),
                stringResource(id = R.string.upcoming_game_tomorrow)
            )
            in 2..Int.MAX_VALUE -> ForecastContent(
                stringResource(id = R.string.upcoming_game_in),
                stringResource(id = R.string.upcoming_game_in_days, inDays)
            )
            else -> Placeholder()
        }
    } ?: Placeholder()
}

@Composable
private fun Placeholder() {
    ForecastContent("", "")
}

@Composable
private fun ForecastContent(caption: String, value: String, isHighlight: Boolean = true) {
    Text(
        text = caption,
        style = MaterialTheme.typography.titleLarge
    )
    Spacer(modifier = Modifier.size(6.dp))
    Text(
        text = value,
        style = MaterialTheme.typography.headlineSmall,
        color = if (isHighlight) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    )
}