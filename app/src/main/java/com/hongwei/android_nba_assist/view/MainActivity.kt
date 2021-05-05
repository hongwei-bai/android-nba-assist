package com.hongwei.android_nba_assist.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.view.main.MainScreen
import com.hongwei.android_nba_assist.viewmodel.SplashViewModel
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
            val rootNavController = rememberNavController()
            val mainNavController = rememberNavController()
            NavComposeApp(
                rootNavController,
                mainNavController
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp(
    rootNavController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(rootNavController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(rootNavController)
        }
        composable("main") {
            MainScreen(mainNavController)
        }
    }
}