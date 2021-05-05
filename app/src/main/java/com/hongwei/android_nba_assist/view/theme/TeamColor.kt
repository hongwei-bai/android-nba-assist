package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val WarriorWhite = Color(0xffffffff)
val WarriorBlue = Color(0xff04539c)
val WarriorGold = Color(0xfffcb826)

val LakersWhite = Color(0xffffffff)
val LakersGold = Color(0xffFDB827)
val LakersPurple = Color(0xff542583)

val LakersLightColors = lightColors(
    primary = LakersGold,
    primaryVariant = LakersGold,
    onPrimary = LakersPurple,
    secondary = LakersWhite,
    secondaryVariant = LakersWhite,
    onSecondary = LakersPurple,
    background = LakersGold,
    onBackground = LakersPurple,
    error = LakersGold,
    onError = LakersPurple
)

val GSLightColors = lightColors(
    primary = WarriorWhite,
    primaryVariant = WarriorWhite,
    onPrimary = WarriorBlue,
    secondary = WarriorBlue,
    secondaryVariant = WarriorBlue,
    onSecondary = WarriorWhite,
    background = WarriorWhite,
    onBackground = WarriorBlue,
    error = WarriorWhite,
    onError = WarriorBlue
)
