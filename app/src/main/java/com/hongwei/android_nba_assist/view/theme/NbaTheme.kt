package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NbaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        typography = NbaTypography,
        content = content
    )
}