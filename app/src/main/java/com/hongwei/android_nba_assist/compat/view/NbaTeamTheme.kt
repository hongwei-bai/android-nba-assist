package com.hongwei.android_nba_assist.compat.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

interface NbaTeamTheme {
    fun setTeamTheme(team: String)

    companion object {
        private const val SHARED_PREFERENCES_NAME = "team_preference"

        private const val SHARED_PREFERENCES_KEY_MY_TEAM = "my_team"

        fun Context.getMyTeam(): String =
            getSharedPreferences(SHARED_PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE)
                .getString(SHARED_PREFERENCES_KEY_MY_TEAM, "gsw") ?: "gsw"

        fun Context.setMyTeam(team: String) {
            with(getSharedPreferences(SHARED_PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE).edit()) {
                putString(SHARED_PREFERENCES_KEY_MY_TEAM, team)
                apply()
            }
        }
    }
}