package com.hongwei.android_nba_assist.view.season

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hongwei.android_nba_assist.view.theme.BlackAlphaA0

@Composable
fun PlayOff() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = BlackAlphaA0)
                .verticalScroll(ScrollState(0))
        ) {

        }
    }
}