package com.hongwei.android_nba_assist.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.view.share.LoadingDots

@Preview
@Composable
fun GamesLeftView() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "10",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.secondary
        )
        Text(
            text = "Games Left",
            style = MaterialTheme.typography.h5
        )
    }
}