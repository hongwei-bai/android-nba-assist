package com.hongwei.android_nba_assistant.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hongwei.android_nba_assistant.viewmodel.WarriorsCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WarriorsCalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavComposeApp()
        }

        viewModel.matchEvents.observe(this) {
//            it.forEach { event ->
//                println("vs ${event.opponent} @ ${event.date}")
//            }
        }
    }
}