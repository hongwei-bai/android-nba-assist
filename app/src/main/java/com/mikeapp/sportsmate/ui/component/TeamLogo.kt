package com.mikeapp.sportsmate.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

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
        val painter = rememberAsyncImagePainter(
            model = logoUrl,
            //TODO
//            builder = {
//                crossfade(true)
//            }
        )
        val statefulPainter = when (painter.state) {
            is AsyncImagePainter.State.Error -> {
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
            //TODO
//                .placeholder(
//                    visible = painter.state is AsyncImagePainter.State.Loading,
//                    highlight = PlaceholderHighlight.shimmer(),
//                )
        )
    }
}