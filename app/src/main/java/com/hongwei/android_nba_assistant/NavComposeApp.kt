package com.hongwei.android_nba_assistant

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "profile") {
        composable("home") { nav_graph.dest.home }
        composable("plant_detail") { nav_graph.dest.plant_detail }
    }

    @Composable
    fun Profile(navController: NavController) {
        Button(onClick = { navController.navigate(nav_graph.dest.plant_detail) }) {
            Text(text = "Navigate next")
        }
    }
}

object nav_graph {

    const val id = 1 // graph id

    object dest {
        const val home = 2
        const val plant_detail = 3
    }

    object action {
        const val to_plant_detail = 4
    }

    object args {
        const val plant_id = "plantId"
    }
}