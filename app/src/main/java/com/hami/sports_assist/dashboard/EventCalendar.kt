package com.hami.sports_assist.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.hami.sports_assist.R
import com.hami.sports_assist.ui.component.TeamLogo
import com.hami.sports_assist.ui.theme.*
import com.hami.sports_assist.util.LocalDateTimeUtil
import com.hami.sports_assist.util.LocalDateTimeUtil.CALENDAR_GAME_DATE_FORMAT
import com.hami.sports_assist.util.LocalDateTimeUtil.getDayIdentifier
import java.util.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Calendar(
    calendarDays: List<List<Calendar>>?, events: List<EventViewObject>?, backgroundUrl: String?,
    screenSize: IntSize
) {
    var cellTextHeight by remember { mutableStateOf(0) }

    if (calendarDays != null && events != null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            backgroundUrl?.let {
                val painter = rememberImagePainter(
                    data = backgroundUrl,
                    builder = { crossfade(true) })
                val statefulPainter = when (painter.state) {
                    is ImagePainter.State.Error -> painterResource(id = R.drawable.bg_placeholder)
                    else -> painter
                }
                Image(
                    painter = statefulPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calendarCellHeight(screenSize, cellTextHeight) * calendarDays.size)
                        .placeholder(
                            visible = painter.state is ImagePainter.State.Loading,
                            highlight = PlaceholderHighlight.shimmer(),
                        )
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.bg_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calendarCellHeight(screenSize, cellTextHeight) * calendarDays.size)
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
                            .height(calendarCellHeight(screenSize, cellTextHeight))
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
                                    getDayIdentifier(event.unixTimeStamp) == day.timeInMillis
                                },
                                onTextLayout = {
                                    if (cellTextHeight == 0) {
                                        cellTextHeight = it.size.height
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier, calendarDay: Calendar, event: EventViewObject?,
    onTextLayout: (TextLayoutResult) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val todayId = getDayIdentifier(System.currentTimeMillis())
        val isToday = todayId == calendarDay.timeInMillis
        val pastDays = todayId > calendarDay.timeInMillis
        val textColor = if (pastDays) {
            Grey60
        } else {
            MaterialTheme.colors.onPrimary
        }
        val logoModifier = Modifier.alpha(if (pastDays) 0.6f else 1f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        ) {
            if (isToday) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    drawPath(
                        path = Path().apply {
                            moveTo((canvasWidth) * 0.5f, canvasHeight)
                            lineTo(canvasWidth * 0.5f + canvasHeight * 0.577f, 0f)
                            lineTo(canvasWidth * 0.5f - canvasHeight * 0.577f, 0f)
                            close()
                        },
                        color = Color.White
                    )
                }
            }
        }
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
                color = textColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
        event?.run {
            Floor(event.home, modifier = logoModifier.padding(bottom = 4.dp), event.opponent)
            Text(
                text = if (event.home) event.opponentLocation.uppercase(Locale.US)
                else stringResource(id = R.string.calendar_game_at_location, event.opponentLocation.uppercase(Locale.US)),
                style = MaterialTheme.typography.caption,
                fontSize = 8.sp,
                fontWeight = ExtraBold,
                textAlign = TextAlign.Center,
                color = textColor
            )

            event.result?.let {
                val isWin = result?.startsWith("W", true) == true
                val resultColor = if (isWin) ColorVictory else ColorLose
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.caption,
                    fontSize = 8.sp,
                    fontWeight = ExtraBold,
                    textAlign = TextAlign.Center,
                    color = resultColor
                )
            } ?: Text(
                text = LocalDateTimeUtil.getLocalTimeDisplay(event.unixTimeStamp),
                style = MaterialTheme.typography.caption,
                fontSize = 8.sp,
                fontWeight = ExtraBold,
                textAlign = TextAlign.Center,
                color = textColor,
                onTextLayout = { onTextLayout.invoke(it) }
            )
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
                        modifier = Modifier
                            .wrapContentWidth()
                    )
                }
            }
        }
    } else {
        Box(modifier = modifier) {
            TeamLogo(
                logoUrl = opponent.logo,
                modifier = Modifier
                    .wrapContentWidth()
            )
        }
    }
}

@Composable
fun calendarCellHeight(screenSize: IntSize, measuredTextHeight: Int): Dp =
    with(LocalDensity.current) {
        val cellWidth = screenSize.width / 7
        val textHeightCoefficient = if (screenSize.height > screenSize.width)
            6.5f else 4.5f
        if (measuredTextHeight > 0) {
            cellWidth.toDp() + (measuredTextHeight * textHeightCoefficient).toDp()
        } else {
            cellWidth.toDp() + 60.dp
        }
    }

data class EventViewObject(
    val unixTimeStamp: Long,
    val opponent: OpponentViewObject,
    val opponentLocation: String,
    val home: Boolean,
    val result: String? = null
)

data class OpponentViewObject(
    val abbrev: String,
    val name: String? = null,
    val logo: String
)