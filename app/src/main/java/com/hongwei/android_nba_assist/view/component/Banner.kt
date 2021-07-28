package com.hongwei.android_nba_assist.view.component

import androidx.compose.foundation.Image
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

@Composable
fun Banner(url: String?, modifier: Modifier = Modifier) {
    val painter = rememberCoilPainter(
        request = url,
        fadeIn = true
    )

    Surface(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
    ) {
        when (painter.loadState) {
            is ImageLoadState.Success -> Image(
                painter = painter,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = modifier
                    .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
            )

//            ImageLoadState.Loading -> LoadingImage(modifier = Modifier.size(48.dp))
            else -> Image(
                painter = painterResource(id = R.drawable.banner_placeholder),
                contentDescription = null,
                modifier = modifier,
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}