package com.hongwei.android_nba_assist.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Home)
    object Standing : Screen("standing", "Standing", Icons.Default.List)
    object Goal : Screen("goal", "Goal", Icons.Default.Star)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}