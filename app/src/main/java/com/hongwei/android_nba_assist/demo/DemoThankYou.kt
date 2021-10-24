package com.hongwei.android_nba_assist.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, backgroundColor = 0xFF133176)
@Composable
fun ThankYou() {
    Text(
        text = "Thank you!",
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
}