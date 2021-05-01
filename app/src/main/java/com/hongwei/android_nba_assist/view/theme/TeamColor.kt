package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val WarriorWhite = Color(0xffffffff)
val WarriorBlue = Color(0xff04539c)
val WarriorGold = Color(0xfffcb826)

val GSLightColors = lightColors(
    primary = WarriorGold ?: WarriorBlue,
    primaryVariant = WarriorWhite,
    onPrimary = WarriorBlue,
    secondary = WarriorBlue,
    secondaryVariant = WarriorBlue,
    onSecondary = WarriorWhite
)