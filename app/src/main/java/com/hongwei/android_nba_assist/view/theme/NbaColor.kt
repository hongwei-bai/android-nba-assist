package com.hongwei.android_nba_assist.view.theme

import android.annotation.SuppressLint
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val NbaWhite = Color(0xFFFFFFFF)
val NbaBlue = Color(0xFF17408B)
val NbaRed = Color(0xFFC9082A)

@SuppressLint("ConflictingOnColor")
val NBALightColors = lightColors(
    primary = NbaBlue,
    primaryVariant = NbaBlue,
    onPrimary = NbaWhite,
    secondary = NbaRed,
    secondaryVariant = NbaRed,
    onSecondary = NbaWhite,
    background = NbaWhite,
    onBackground = NbaBlue,
    error = NbaWhite,
    onError = NbaRed
)
