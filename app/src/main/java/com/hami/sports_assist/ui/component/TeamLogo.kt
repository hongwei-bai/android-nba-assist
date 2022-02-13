package com.hami.sports_assist.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TeamLogo(logoUrl: String? = null, localPlaceholderResId: Int = 0, modifier: Modifier) {
    if (logoUrl == null && localPlaceholderResId > 0) {
        Image(
            painter = painterResource(id = localPlaceholderResId),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
        )
    } else {
        val painter = rememberImagePainter(
            data = logoUrl,
            builder = {
                crossfade(true)
            }
        )
        val statefulPainter = when (painter.state) {
            is ImagePainter.State.Error -> {
                Log.e("teamLogo", "load logo error, url: $logoUrl")
                if (localPlaceholderResId > 0) {
                    painterResource(id = localPlaceholderResId)
                } else {
                    painter
                }
            }
            else -> painter
        }
        Image(
            painter = statefulPainter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .placeholder(
                    visible = painter.state is ImagePainter.State.Loading,
                    highlight = PlaceholderHighlight.shimmer(),
                )
        )
    }
}