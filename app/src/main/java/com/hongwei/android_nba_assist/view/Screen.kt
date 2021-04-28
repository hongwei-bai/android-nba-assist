package com.hongwei.android_nba_assist.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("Dashboard", "Dashboard", Icons.Default.Home)
    object Standing : Screen("Standing", "Standing", Icons.Default.List)
    object Goal : Screen("Goal", "Goal", Icons.Default.Star)
    object Settings : Screen("Settings", "Settings", Icons.Default.Settings)
}