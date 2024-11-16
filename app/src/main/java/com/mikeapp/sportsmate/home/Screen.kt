package com.mikeapp.sportsmate.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Outlined.DateRange)
    object Season : Screen("season", "Season", Icons.Outlined.Menu)
    object News : Screen("news", "News", Icons.Outlined.MailOutline)
    object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}