package com.hongwei.android_nba_assist.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.hongwei.android_nba_assist.view.dashboard.Dashboard
import com.hongwei.android_nba_assist.view.indev.Goal
import com.hongwei.android_nba_assist.view.indev.Settings
import com.hongwei.android_nba_assist.view.navigation.BottomNavBar
import com.hongwei.android_nba_assist.view.season.Season
import com.hongwei.android_nba_assist.view.theme.NbaTeamTheme
import com.hongwei.android_nba_assist.viewmodel.MainViewModel

@Composable
fun MainScreen() {
    val viewModel = hiltNavGraphViewModel<MainViewModel>()
    val navController = rememberNavController()
    NbaTeamTheme(viewModel.teamTheme.observeAsState().value) {
        Scaffold(bottomBar = { BottomNavBar(navController) }) {
            MainNavCompose(navController)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun MainNavCompose(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard()
        }
        composable("season") {
            Season()
        }
        composable("goal") {
            Goal()
        }
        composable("settings") {
            Settings()
        }
    }
}