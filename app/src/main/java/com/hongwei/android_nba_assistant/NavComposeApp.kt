package com.hongwei.android_nba_assistant

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController

@Composable
fun NavComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable("calendar") { Calendar(navController) }
    }
}

@Composable
fun Home(navController: NavController) {
    Button(onClick = { navController.navigate("calendar") }) {
        Text(text = "Navigate next")
    }
}

@Composable
fun Calendar(navController: NavController) {
    Text(text = "NBA Schedule")
}