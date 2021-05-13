package com.hongwei.android_nba_assist.view.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var localSettings: LocalSettings

    @Inject
    lateinit var nbaTeamRepository: NbaTeamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavComposeApp()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("main") {
            MainScreen()
        }
    }
}