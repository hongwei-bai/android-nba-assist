package com.mikeapp.sportsmate.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mikeapp.sportsmate.R

@Composable
fun Banner(url: String?, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
    ) {
        url?.let {
            val painter = rememberAsyncImagePainter(url)
            val statefulPainter = when (painter.state) {
                is AsyncImagePainter.State.Error -> painterResource(id = R.drawable.banner_placeholder)
                else -> painter
            }
            Image(
                painter = statefulPainter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp))
                //TODO
//                    .placeholder(visible = painter.state is AsyncImagePainter.State.Loading)
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