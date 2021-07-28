package com.hongwei.android_nba_assist.view.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberImagePainter
import com.hongwei.android_nba_assist.constant.AppConfigurations

@Composable
fun TeamLogo(logoUrl: String? = null, localPlaceholderResId: Int, modifier: Modifier) {
    if (AppConfigurations.LogoConfiguration.useLocalLogos || logoUrl == null) {
        Image(
            painter = painterResource(id = localPlaceholderResId),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = modifier
        )
    } else {
        Image(
            painter = rememberImagePainter(
                data = logoUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = modifier
        )
    }
}