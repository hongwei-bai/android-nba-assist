package com.hami.sports_assist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TeamLogo(logoUrl: String? = null, localPlaceholderResId: Int, modifier: Modifier) {
    val painter = rememberImagePainter(
        data = logoUrl,
        builder = {
            crossfade(true)
        }
    )
    val statefulPainter = when (painter.state) {
        is ImagePainter.State.Error -> null
        else -> painter
    }
    statefulPainter?.let {
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