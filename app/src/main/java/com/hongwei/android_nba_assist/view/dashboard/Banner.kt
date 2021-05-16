package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.view.animation.LoadingDots

@Composable
fun Banner(url: String?) {
    val painter = rememberCoilPainter(
        request = url,
        fadeIn = true
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
    ) {
        when (painter.loadState) {
            is ImageLoadState.Success -> Image(
                painter = painter,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier
                    .height(bannerHeight)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
            )
            ImageLoadState.Loading -> LoadingDots(Modifier.height(bannerHeight))
            else -> Image(
                painter = painterResource(id = R.drawable.banner_placeholder),
                contentDescription = null,
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

private val bannerHeight = 150.dp