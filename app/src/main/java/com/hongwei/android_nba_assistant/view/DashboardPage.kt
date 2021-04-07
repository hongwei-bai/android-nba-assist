package com.hongwei.android_nba_assistant.view

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.navigate

@Composable
fun Dashboard(navController: NavController) {
    Button(onClick = { navController.navigate("team-mock/gs") }) {
        Text(text = "Navigate next")
    }
}