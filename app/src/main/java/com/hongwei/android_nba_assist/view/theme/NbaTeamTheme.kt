package com.hongwei.android_nba_assist.view.theme

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun NbaTeamTheme(
    team1: String,
    content: @Composable () -> Unit
) {
    when (team1) {
        "gs" -> MaterialTheme(
            colors = GSLightColors,
            typography = NbaTypography,
            content = content
        )
        else -> MaterialTheme(
            colors = LightColors,
            typography = NbaTypography,
            content = content
        )
    }

    CompositionLocalProvider(
        teamTitleColor provides rememberedColors,
        teamTitleStyle provides remembered
    ) {
        ProvideTextStyle(value = typography.body1, content = content)
    }
}