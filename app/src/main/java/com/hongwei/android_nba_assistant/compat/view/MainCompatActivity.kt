package com.hongwei.android_nba_assistant.compat.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hongwei.android_nba_assistant.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCompatActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = this.findNavController(R.id.nav_fragment)
    }
}