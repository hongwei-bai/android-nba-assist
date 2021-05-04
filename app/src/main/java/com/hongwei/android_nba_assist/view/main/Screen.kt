package com.hongwei.android_nba_assist.view.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Outlined.Alarm)
    object Standing : Screen("standing", "Standing", Icons.Outlined.TrendingUp)
    object Goal : Screen("goal", "Goal", Icons.Outlined.Star)
    object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}