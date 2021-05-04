package com.hongwei.android_nba_assist.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.hongwei.android_nba_assist.view.Goal
import com.hongwei.android_nba_assist.view.Settings
import com.hongwei.android_nba_assist.view.TeamSchedule
import com.hongwei.android_nba_assist.view.dashboard.Dashboard
import com.hongwei.android_nba_assist.view.navigation.BottomNavBar

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    //            NbaTeamTheme(teamViewModel.team.observeAsState().value) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) {
        MainNavCompose(
            navController,
            ""//teamViewModel.teamBannerUrl.observeAsState().value
        )
    }
//            }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainNavCompose(
    navController: NavHostController,
    teamBannerUrl: String?
) {
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
            Goal()
        }
        composable("settings") {
            Settings()
        }
    }
}