package com.hongwei.android_nba_assist.compat.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.compat.view.NbaTeamTheme.Companion.getMyTeam
import com.hongwei.android_nba_assist.compat.view.NbaTeamTheme.Companion.setMyTeam
import com.hongwei.android_nba_assist.datasource.firebase.CloudMessaging
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getStyleByName
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainCompatActivity : AppCompatActivity(), NbaTeamTheme {
    private lateinit var navController: NavController

    @Inject
    lateinit var cloudMessaging: CloudMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getStyleByName(this, "Theme.Nba.${getMyTeam().toUpperCase(Locale.ROOT)}"))
        setContentView(R.layout.activity_main)
        navController = this.findNavController(R.id.nav_fragment)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        cloudMessaging.register()
    }

    override fun setTeamTheme(team: String) {
        if (getMyTeam() != team) {
            setMyTeam(team)
            finish()
            startActivity(Intent(this, MainCompatActivity::class.java))
        }
    }
}