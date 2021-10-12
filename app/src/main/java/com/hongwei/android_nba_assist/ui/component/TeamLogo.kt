package com.hongwei.android_nba_assist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hongwei.android_nba_assist.constant.AppConfigurations

@ExperimentalCoilApi
@Composable
fun TeamLogo(logoUrl: String? = null, localPlaceholderResId: Int, modifier: Modifier) {
    if (AppConfigurations.LogoConfiguration.useLocalLogos || logoUrl == null) {
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
            is ImagePainter.State.Error -> painterResource(localPlaceholderResId)
            else -> painter
        }
        Image(
            painter = statefulPainter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .fillMaxSize()
                .placeholder(
                    visible = painter.state is ImagePainter.State.Loading,
                    highlight = PlaceholderHighlight.shimmer(),
                )
        )
    }
}