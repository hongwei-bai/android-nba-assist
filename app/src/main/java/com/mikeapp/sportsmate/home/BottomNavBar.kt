package com.mikeapp.sportsmate.home

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val items = listOf(Screen.Dashboard, Screen.Season, Screen.News, Screen.Settings)
        items.forEach {
            NavigationBarItem(
                icon = { Icon(it.icon, "") },
                selected = currentRoute == it.route,
                label = { Text(text = it.label) },
                onClick = {
                    navController.popBackStack(
                        navController.graph.startDestinationId, false
                    )
                    if (currentRoute != it.route) {
                        if (it.route == "dashboard") {
                            navController.navigate(it.route) {
                                popUpTo("dashboard") { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(it.route)
                        }
                    }
                }
            )
        }
    }
}