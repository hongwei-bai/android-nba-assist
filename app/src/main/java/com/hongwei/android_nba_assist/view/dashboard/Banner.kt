package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.view.share.LoadingDots

@Preview
@Composable
fun Banner() {
    val painter = rememberCoilPainter(
        request = "https://hongwei-test1.top/resize/1080/nba_v1/banner/gs.jpg",
        fadeIn = true,
        previewPlaceholder = R.drawable.banner_placeholder
    )

    when (painter.loadState) {
        is ImageLoadState.Success -> Image(
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                .shadow(10.dp)
        )
        ImageLoadState.Loading -> LoadingDots()
        else -> Image(
            painter = painterResource(id = R.drawable.banner_placeholder),
            contentDescription = null,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
    }
}