package com.hongwei.android_nba_assist.datasource.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

object AppSettings {
    lateinit var _myTeam: String

    val myTeam: String
        get() = _myTeam

    fun initialize(context: Context) {
        _myTeam = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString("MyTeam", "gs") ?: "gs"
    }

    fun setTeam(context: Context, team: String) {
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putString("MyTeam", team)
            .apply()
    }

    var scheduleWeeks: Int = 2

    var weekStartsFromMonday: Boolean = true
}