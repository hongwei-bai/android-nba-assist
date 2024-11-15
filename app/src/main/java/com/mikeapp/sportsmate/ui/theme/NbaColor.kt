package com.mikeapp.sportsmate.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.lightColorSchemecheme
import androidx.compose.ui.graphics.Color

val NbaWhite = Color(0xFFFFFFFF)
val NbaBlue = Color(0xFF17408B)
val NbaRed = Color(0xFFC9082A)

val ColorVictory = Gold900
val ColorLose = Green900

@SuppressLint("ConflictingOnColor")
val NBALightColors = lightColorScheme(
    primary = NbaBlue,
    primaryContainer = NbaBlue,
    onPrimary = NbaWhite,
    secondary = NbaRed,
    secondaryContainer = NbaRed,
    onSecondary = NbaWhite,
    background = NbaWhite,
    onBackground = NbaBlue,
    error = NbaWhite,
    onError = NbaRed
)
