package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.hongwei.android_nba_assist.viewmodel.NbaViewModel

@Composable
fun Dashboard(
    navController: NavController,
    viewModel: NbaViewModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Banner()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "10"
            )
            Text(text = "Games Left")
        }
        Text(text = "Upcoming game on")
        Text(text = "Today!!")
        Button(onClick = {
            viewModel.toggle()
//        navController.navigate("standing")
        }) {
            Text(text = "Navigate next")
        }
    }
}

