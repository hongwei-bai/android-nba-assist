package com.hongwei.android_nba_assist.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DemoPageWithOneButton() {
    val demoViewModel = hiltViewModel<DemoViewModel>()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(demoViewModel.color.observeAsState().value!!),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { demoViewModel.altColor() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Alt color")
        }
    }
}