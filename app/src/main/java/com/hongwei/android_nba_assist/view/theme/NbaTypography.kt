package com.hongwei.android_nba_assist.view.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hongwei.android_nba_assist.R

val NbaTypography = Typography(
    h4 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = FontFamily(Font(R.font.play, FontWeight.Light)),
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)