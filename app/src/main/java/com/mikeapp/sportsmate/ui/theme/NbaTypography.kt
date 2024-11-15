package com.mikeapp.sportsmate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mikeapp.sportsmate.R

val customFontFamily = FontFamily(Font(R.font.play_regular))

val NbaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = customFontFamily, // Apply it once
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    displayMedium = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    titleMedium = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),
    titleSmall = TextStyle(
        fontFamily = customFontFamily,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = customFontFamily,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = customFontFamily,
        fontSize = 12.sp // Equivalent to caption
    ),
    labelLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    )
)
