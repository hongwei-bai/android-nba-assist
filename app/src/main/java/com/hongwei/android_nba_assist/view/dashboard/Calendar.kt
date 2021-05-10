package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongwei.android_nba_assist.datasource.room.Event
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.CALENDAR_GAME_DATE_FORMAT
import com.hongwei.android_nba_assist.view.component.TeamLogo
import java.util.*

@Composable
fun Calendar(calendarDays: List<List<Calendar>>?, events: List<Event>?) {
    if (calendarDays != null && events != null) {
        calendarDays.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = MaterialTheme.colors.primary)
            ) {
                it.forEach { day ->
                    CalendarDay(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.0f, true)
                            .padding(5.dp)
                            .background(Color.Gray),
                        calendarDay = day,
                        event = events.firstOrNull { event ->
                            LocalDateTimeUtil.getDayIdentifier(event.unixTimeStamp) == day.timeInMillis
                        })
                }
            }
        }
    }
}

@Composable
fun CalendarDay(modifier: Modifier, calendarDay: Calendar, event: Event?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Text(
                text = LocalDateTimeUtil.getLocalDateDisplay(calendarDay, CALENDAR_GAME_DATE_FORMAT),
                style = MaterialTheme.typography.caption,
                fontSize = 7.sp,
                fontWeight = W500,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        event?.run {
            TeamLogo(
                team = opponent,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}