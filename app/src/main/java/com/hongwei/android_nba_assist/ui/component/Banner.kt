package com.hongwei.android_nba_assist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.hongwei.android_nba_assist.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Banner(url: String?, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
    ) {
        url?.let {
            val painter = rememberImagePainter(url)
            val statefulPainter = when (painter.state) {
                is ImagePainter.State.Error -> painterResource(id = R.drawable.banner_placeholder)
                else -> painter
            }
            Image(
                painter = statefulPainter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .placeholder(
                        visible = painter.state is ImagePainter.State.Loading,
                        highlight = PlaceholderHighlight.shimmer(),
                    )
                    .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.banner_placeholder),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
        )
    }
}