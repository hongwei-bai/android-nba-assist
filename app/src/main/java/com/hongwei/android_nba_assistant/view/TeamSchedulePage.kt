package com.hongwei.android_nba_assistant.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val WEEKDAYS: List<String> = listOf(
    "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
)

val colors: List<Color> = listOf(
    Color.Blue, Color.Red, Color.Black, Color.Cyan, Color.Gray, Color.Green, Color.Magenta
)

@ExperimentalFoundationApi
@Preview
@Composable
fun testPreview() {
    TeamSchedule("gs")
}

@ExperimentalFoundationApi
@Composable
fun TeamSchedule(team: String) {
    Column {
        Row {
            WEEKDAYS.forEach { weekDay ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f)
                        .height(40.dp)
                        .background(Color.Gray)
                        .wrapContentSize(Alignment.Center)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = weekDay
                    )
                }
            }
        }
        Row {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 128.dp)
            ) {
                for (i in 1..30) {
                    item {
                        Text("$i")
                    }
                }
            }
        }
    }
}