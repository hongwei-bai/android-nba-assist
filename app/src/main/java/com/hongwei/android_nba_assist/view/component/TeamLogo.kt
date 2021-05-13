package com.hongwei.android_nba_assist.view.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun TeamLogo(logoUrl: String, localPlaceholderResId: Int, modifier: Modifier) {
    val painter = rememberCoilPainter(
        request = logoUrl,
        fadeIn = true
    )
    when (painter.loadState) {
        is ImageLoadState.Success -> Image(
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = modifier
        )
        else -> Image(
            painter = painterResource(id = localPlaceholderResId),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.FillWidth,
        )
    }
}