package com.hongwei.android_nba_assist.view.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.hongwei.android_nba_assist.datasource.room.Team
import com.hongwei.android_nba_assist.util.ResourceByNameUtil

@Composable
fun TeamLogo(team: Team, modifier: Modifier) {
    val painter = rememberCoilPainter(
        request = team.logo,
        fadeIn = true
    )
    when (painter.loadState) {
        is ImageLoadState.Success -> Image(
            painter = painter,
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
            modifier = modifier
        )
        else -> Image(
            painter = painterResource(id = ResourceByNameUtil.getResourceIdByName(LocalContext.current, team.abbrev)),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.FillHeight,
        )
    }
}