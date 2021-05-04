package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

@Composable
fun Dashboard(
    navHostController: NavHostController
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Banner("")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "10",
                color = MaterialTheme.colors.primary
            )
            Text(text = "Games Left")
        }
        Text(text = "Upcoming game on")
        Text(text = "Today!!")
        Button(onClick = {
//            EventBus.getDefault().post(NbaTeamSwitchEvent())
        }) {
            Text(text = "Switch to gs")
        }
        Button(onClick = {
//            EventBus.getDefault().post(NbaTeamSwitchEvent("lal"))
        }) {
            Text(text = "Switch to lal")
        }
        Button(onClick = {
            navHostController.navigate("standing")
        }) {
            Text(text = "Navigate to Standing")
        }
    }
}

