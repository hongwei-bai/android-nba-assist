package com.hongwei.android_nba_assist.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.viewmodel.NbaViewModel

@Composable
fun Dashboard(
    navController: NavController,
    viewModel: NbaViewModel
) {
    Column {
        Surface(
            shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            Image(
                painterResource(id = R.drawable.banner_gsw1080),
                contentDescription = null,
            )
        }
        Button(onClick = {
            viewModel.toggle()
//        navController.navigate("standing")
        }) {
            Text(text = "Navigate next")
        }
    }
}