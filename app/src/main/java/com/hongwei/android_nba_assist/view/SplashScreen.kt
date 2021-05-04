package com.hongwei.android_nba_assist.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.LottieAnimationState
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    localSettings: LocalSettings,
    nbaTeamRepository: NbaTeamRepository
) {
    val viewModel: SplashViewModel = viewModel(null,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                SplashViewModel(
                    localSettings = localSettings,
                    nbaTeamRepository = nbaTeamRepository
                ) as T
        })

    val animationSpec = remember { LottieAnimationSpec.RawRes(R.raw.anim_splash) }
    val state = LottieAnimationState(isPlaying = true, repeatCount = Integer.MAX_VALUE)
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
            Text(stringResource(id = R.string.splash_loading_text))
        }
    }
}