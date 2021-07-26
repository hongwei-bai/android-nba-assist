package com.hongwei.android_nba_assist.view.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.LottieAnimationState
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.viewmodel.SplashViewModel


@Composable
fun SplashScreen(navController: NavController) {
    val splashViewModel = hiltViewModel<SplashViewModel>()
    splashViewModel.preload {
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
            launchSingleTop = true
        }
    }
    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.anim_splash) }
    val state = LottieAnimationState(
        isPlaying = true,
        repeatCount = Integer.MAX_VALUE,
        enableMergePaths = false
    )
    state.speed = 1.2f
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                spec = animationSpec,
                animationState = state
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = stringResource(id = R.string.splash_loading_text),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5
            )
        }
    }
}