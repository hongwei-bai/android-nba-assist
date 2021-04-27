package com.hongwei.android_nba_assist.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { Dashboard(navController) }
        composable(
            "team-mock/{team}",
            arguments = listOf(
                navArgument("team") { type = NavType.StringType }
            )
        ) {
            TeamSchedule("gs")
        }
    }
}