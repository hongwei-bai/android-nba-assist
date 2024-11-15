package com.mikeapp.sportsmate.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mikeapp.sportsmate.data.NbaTeamRepository
import com.mikeapp.sportsmate.ui.theme.NbaTeamTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var nbaTeamRepository: NbaTeamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<HomeViewModel>()

            NbaTeamTheme(viewModel.teamTheme.observeAsState().value) {
                //TODO
//                SystemUiController()

                NavComposeApp()
            }
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }
}

//@Composable
//fun SystemUiController() {
//    val systemUiController = rememberSystemUiController()
//    val useDarkIcons = false //MaterialTheme.colorScheme.isLight
//    val systemBarColor = MaterialTheme.colorScheme.primary
//
//    SideEffect {
//        // Update all of the system bar colors to be transparent, and use
//        // dark icons if we're in light theme
//        systemUiController.setSystemBarsColor(
//            color = systemBarColor,
//            darkIcons = useDarkIcons
//        )
//
//        // setStatusBarsColor() and setNavigationBarsColor() also exist
//    }
//}

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