package com.hongwei.android_nba_assist.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard(navController)
        }
        composable(
            "standing",
            arguments = listOf(
                navArgument("team") { type = NavType.StringType }
            )
        ) {
            TeamSchedule("gs")
        }
        composable("goal") {
            Goal(navController)
        }
        composable("settings") {
            Settings(navController)
        }
    }
}