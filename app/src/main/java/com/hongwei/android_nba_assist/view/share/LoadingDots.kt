package com.hongwei.android_nba_assist.view.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.LottieAnimationState
import com.hongwei.android_nba_assist.R

@Preview
@Composable
fun LoadingDots() {
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.loading_dots) }
    val state = LottieAnimationState(isPlaying = true, repeatCount = Integer.MAX_VALUE)
    state.speed = 0.75f
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            spec = animationSpec,
            modifier = Modifier.size(50.dp),
            animationState = state
        )
    }
}