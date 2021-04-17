package com.hongwei.android_nba_assistant.compat.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hongwei.android_nba_assistant.R
import com.hongwei.android_nba_assistant.compat.view.NbaTeamTheme.Companion.getMyTeam
import com.hongwei.android_nba_assistant.compat.view.NbaTeamTheme.Companion.setMyTeam
import com.hongwei.android_nba_assistant.util.ResourceByNameUtil.getStyleByName
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainCompatActivity : AppCompatActivity(), NbaTeamTheme {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getStyleByName(this, getMyTeam().toUpperCase(Locale.US)))
        setContentView(R.layout.activity_main)
        navController = this.findNavController(R.id.nav_fragment)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    override fun setTeamTheme(team: String) {
        setTheme(R.style.Theme_NbaAssistant_DayNight_LAK)

        if (getMyTeam() != team) {
            setMyTeam(team)
            finish()
            startActivity(Intent(this, MainCompatActivity::class.java))
        }
    }
}