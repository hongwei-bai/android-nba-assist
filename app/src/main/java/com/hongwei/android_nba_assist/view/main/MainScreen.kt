package com.hongwei.android_nba_assist.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hongwei.android_nba_assist.view.Goal
import com.hongwei.android_nba_assist.view.Settings
import com.hongwei.android_nba_assist.view.TeamSchedule
import com.hongwei.android_nba_assist.view.dashboard.Dashboard
import com.hongwei.android_nba_assist.view.navigation.BottomNavBar
import com.hongwei.android_nba_assist.view.theme.NbaTeamTheme
import com.hongwei.android_nba_assist.viewmodel.MainViewModel
import com.hongwei.android_nba_assist.viewmodel.SplashViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val viewModel = hiltNavGraphViewModel<MainViewModel>()
    NbaTeamTheme(viewModel.teamTheme.observeAsState().value) {
        Scaffold(bottomBar = { BottomNavBar(navController) }) {
            MainNavCompose(navController)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainNavCompose(
    navController: NavHostController
) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard(navController)
        }
        composable("standing") {
            TeamSchedule()
        }
        composable("goal") {
            Goal()
        }
        composable("settings") {
            Settings()
        }
    }
}