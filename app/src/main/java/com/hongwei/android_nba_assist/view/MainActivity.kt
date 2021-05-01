package com.hongwei.android_nba_assist.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.hongwei.android_nba_assist.redux.NavigationReducer
import com.hongwei.android_nba_assist.view.dashboard.Dashboard
import com.hongwei.android_nba_assist.view.navigation.BottomNavBar
import com.hongwei.android_nba_assist.view.theme.NbaTeamTheme
import com.hongwei.android_nba_assist.viewmodel.NbaTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val teamViewModel: NbaTeamViewModel by viewModels()

    @Inject
    lateinit var reducer: NavigationReducer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reducer.register()
        teamViewModel.start()

        setContent {
            val navController = rememberNavController()
            reducer.navHostController = navController
            NbaTeamTheme(teamViewModel.team.observeAsState().value) {
                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) {
                    NavComposeApp(
                        navController,
                        teamViewModel.teamBannerUrl.observeAsState().value
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        reducer.unregister()
        super.onDestroy()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp(
    navController: NavHostController,
    teamBannerUrl: String?
) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard(teamBannerUrl)
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