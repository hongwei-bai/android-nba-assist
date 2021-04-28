package com.hongwei.android_nba_assist.view

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hongwei.android_nba_assist.viewmodel.TeamCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: TeamCalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            Scaffold(
                topBar = {},
                bottomBar = {
                    val items = listOf(Screen.Dashboard, Screen.Standing, Screen.Goal, Screen.Settings)
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

                        items.forEach {
                            BottomNavigationItem(
                                icon = { Icon(it.icon, "") },
                                selected = currentRoute == it.route,
                                label = { Text(text = it.label) },
                                onClick = {
                                    navController.popBackStack(
                                        navController.graph.startDestination, false
                                    )
                                    if (currentRoute != it.route) {
                                        navController.navigate(Uri.parse(it.route))
                                    }
                                })
                        }
                    }
                }
            ) {
                NavComposeApp(navController)
            }
        }

        viewModel.matchEvents.observe(this) {
//            it.forEach { event ->
//                println("vs ${event.opponent} @ ${event.date}")
//            }
        }
    }
}