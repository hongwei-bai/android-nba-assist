package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.CALENDAR_GAME_DATE_FORMAT
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getLocalTimeDisplay
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getResourceIdByName
import com.hongwei.android_nba_assist.view.component.TeamLogo
import com.hongwei.android_nba_assist.view.theme.BlackAlpha60
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0
import com.hongwei.android_nba_assist.view.theme.BlackAlphaE0
import java.util.*

@Composable
fun Calendar(calendarDays: List<List<Calendar>>?, events: List<EventViewObject>?) {
    val weekHeight = 120
    if (calendarDays != null && events != null) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_gs),
                modifier = Modifier
                    .fillMaxWidth()
                    .height((calendarDays.size * weekHeight).dp),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Column(
                modifier = Modifier.background(color = MaterialTheme.colors.primary.copy(alpha = 0.33f))
            ) {
                calendarDays.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(weekHeight.dp)
                            .background(color = BlackAlpha60)
                    ) {
                        it.forEach { day ->
                            CalendarDay(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxHeight()
                                    .weight(1.0f, true)
                                    .background(color = BlackAlpha60),
                                calendarDay = day,
                                event = events.firstOrNull { event ->
                                    LocalDateTimeUtil.getDayIdentifier(event.unixTimeStamp) == day.timeInMillis
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDay(modifier: Modifier, calendarDay: Calendar, event: EventViewObject?) {
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
                fontWeight = Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }
        event?.run {
            Floor(event.home, modifier = Modifier.padding(bottom = 4.dp), event.opponent)
            Text(
                text = if (event.home) event.location.toUpperCase(Locale.US)
                else stringResource(id = R.string.calendar_game_at_location, event.location.toUpperCase(Locale.US)),
                style = MaterialTheme.typography.caption,
                fontSize = 8.sp,
                fontWeight = ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = getLocalTimeDisplay(event.unixTimeStamp),
                style = MaterialTheme.typography.caption,
                fontSize = 8.sp,
                fontWeight = ExtraBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
            )
            event.result?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.caption,
                    fontSize = 8.sp,
                    fontWeight = ExtraBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun Floor(home: Boolean, modifier: Modifier, opponent: OpponentViewObject) {
    val floorBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.secondary,
            MaterialTheme.colors.secondary.copy(alpha = 0.95f),
            MaterialTheme.colors.secondary.copy(alpha = 0.9f)
        ),
        startY = Float.POSITIVE_INFINITY,
        endY = 0f,
        tileMode = TileMode.Clamp
    )
    val topBrush = Brush.radialGradient(
        colors = listOf(BlackAlphaA0, Color.Transparent),
        center = Offset(0f, 0f),
        radius = 40f,
        tileMode = TileMode.Clamp
    )
    val bottomBrush = Brush.radialGradient(
        colors = listOf(BlackAlphaE0, Color.Transparent),
        center = Offset(0f, Float.POSITIVE_INFINITY),
        radius = 80f,
        tileMode = TileMode.Clamp
    )

    if (home) {
        Box(modifier = modifier.background(floorBrush)) {
            Box(modifier = Modifier.background(topBrush)) {
                Box(modifier = Modifier.background(bottomBrush)) {
                    TeamLogo(
                        logoUrl = opponent.logo,
                        localPlaceholderResId = getResourceIdByName(
                            LocalContext.current,
                            opponent.abbrev
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    } else {
        Box(modifier = modifier) {
            TeamLogo(
                logoUrl = opponent.logo,
                localPlaceholderResId = getResourceIdByName(
                    LocalContext.current,
                    opponent.abbrev
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

data class EventViewObject(
    val unixTimeStamp: Long,
    val opponent: OpponentViewObject,
    val location: String,
    val home: Boolean,
    val result: String? = null
)

data class OpponentViewObject(
    val abbrev: String,
    val name: String? = null,
    val logo: String
)