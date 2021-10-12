package com.hongwei.android_nba_assist.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Outlined.Alarm)
    object Season : Screen("season", "Season", Icons.Outlined.TrendingUp)
    object News : Screen("news", "News", Icons.Outlined.Star)
    object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}