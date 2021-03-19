package com.hongwei.android_nba_assistant

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable(
            "team-schedule/{team}",
            arguments = listOf(
                navArgument("team") { type = NavType.StringType }
            )
        ) {
            TeamSchedule(navController, "gs")
        }
    }
}

@Composable
fun Home(navController: NavController) {
    Button(onClick = { navController.navigate("team-schedule/gs") }) {
        Text(text = "Navigate next")
    }
}

@Composable
fun TeamSchedule(navController: NavController, team: String) {
    Text(text = "$team Schedule")
}