package com.hami.sports_assist.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun SolidColorBar(height: Dp, color: Color) {
    Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        drawRect(
                color = color,
                topLeft = Offset(0f, 0f),
                size = Size(size.width, size.height)
        )
    }
}