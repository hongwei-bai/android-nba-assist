package com.hongwei.android_nba_assist.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.hongwei.android_nba_assist.dashboard.Dashboard
import com.hongwei.android_nba_assist.news.News
import com.hongwei.android_nba_assist.season.Season
import com.hongwei.android_nba_assist.settings.Settings
import com.hongwei.android_nba_assist.ui.navigation.BottomNavBar

private const val SLIDE_IN_OFFSET = 1000

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    var size by remember { mutableStateOf(IntSize.Zero) }

    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .onSizeChanged { size = it }) {
            MainNavCompose(navController, size)
        }
    }
}


@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun MainNavCompose(navController: NavHostController, size: IntSize) {
    val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)

    AnimatedNavHost(
        navController = navController,
        startDestination = "dashboard",
    ) {
        composable(
            route = "dashboard",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { size.width }, animationSpec = springSpec)
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -size.width }, animationSpec = springSpec)
            },
        ) {
            Dashboard()
        }
        composable(
            route = "season",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { size.width }, animationSpec = springSpec)
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -size.width }, animationSpec = springSpec)
            },
        ) {
            Season()
        }
        composable(
            route = "news",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { size.width }, animationSpec = springSpec)
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -size.width }, animationSpec = springSpec)
            },
        ) {
            News()
        }
        composable(
            route = "settings",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { size.width }, animationSpec = springSpec)
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -size.width }, animationSpec = springSpec)
            },
        ) {
            Settings()
        }
    }
}