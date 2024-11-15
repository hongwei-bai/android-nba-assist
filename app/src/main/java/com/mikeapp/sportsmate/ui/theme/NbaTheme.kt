package com.mikeapp.sportsmate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun NbaTheme(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = NbaTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}