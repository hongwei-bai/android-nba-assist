package com.hongwei.android_nba_assist.view.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.hongwei.android_nba_assist.view.dashboard.Dashboard
import com.hongwei.android_nba_assist.view.Goal
import com.hongwei.android_nba_assist.view.Settings
import com.hongwei.android_nba_assist.view.TeamSchedule
import com.hongwei.android_nba_assist.viewmodel.NbaTeamViewModel

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        val items = listOf(Screen.Dashboard, Screen.Standing, Screen.Goal, Screen.Settings)
        items.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icon, "") },
                selected = currentRoute == it.route,
                label = { Text(text = it.label) },
                onClick = {
                    navController.popBackStack(
                        navController.graph.startDestination, false
                    )
                    if (currentRoute != it.route) {
                        navController.navigate(it.route)
                    }
                }
            )
        }
    }
}