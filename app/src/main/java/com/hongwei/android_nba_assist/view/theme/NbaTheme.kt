package com.hongwei.android_nba_assist.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NbaTheme(
    colors: Colors = MaterialTheme.colors,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors,
        typography = NbaTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}