package com.hongwei.android_nba_assist.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hongwei.android_nba_assist.R


@Composable
fun SplashScreen(navController: NavController) {
    val splashViewModel = hiltViewModel<SplashViewModel>()
    val preFetchCompleted = splashViewModel.preFetchCompleted.observeAsState().value
    if (preFetchCompleted == true) {
        navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
            launchSingleTop = true
        }
    }

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_splash))
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                speed = 1.2f,
                iterations = LottieConstants.IterateForever,
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