package com.hongwei.android_nba_assist.view.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.hongwei.android_nba_assist.view.Dashboard
import com.hongwei.android_nba_assist.view.Goal
import com.hongwei.android_nba_assist.view.Settings
import com.hongwei.android_nba_assist.view.TeamSchedule
import com.hongwei.android_nba_assist.viewmodel.NbaViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp(navController: NavHostController, viewModel: NbaViewModel) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard(navController, viewModel)
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