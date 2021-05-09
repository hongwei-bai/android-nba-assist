package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hongwei.android_nba_assist.R

val NbaTypography = Typography(
    defaultFontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),

    h4 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)