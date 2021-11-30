package com.hami.sports_assist.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlin.properties.Delegates

object AppSettings {
    private lateinit var _myNbaTeam: String

    private lateinit var _myEuroSoccerTeam: String

    private var _scheduleWeeks by Delegates.notNull<Int>()

    private var _startsFromMonday by Delegates.notNull<Boolean>()

    private var savedValueForScheduleWeeks by Delegates.notNull<Int>()

    private var savedValueStartsFromMonday by Delegates.notNull<Boolean>()

    val myNbaTeam: String
        get() = _myNbaTeam

    val myEuroSoccerTeam: String
        get() = _myEuroSoccerTeam

    val scheduleWeeks: Int
        get() = _scheduleWeeks

    val weekStartsFromMonday: Boolean
        get() = _startsFromMonday

    fun initialize(context: Context) {
        _myNbaTeam = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString(MY_NBA_TEAM_KEY, "gs") ?: "gs"
        _myEuroSoccerTeam = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString(MY_EURO_SOCCER_TEAM_KEY, "mil") ?: "mil"
        _scheduleWeeks = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getInt("scheduleWeeks", 2)
        _startsFromMonday = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getBoolean("weekStartsFromMonday", true)
        savedValueForScheduleWeeks = _scheduleWeeks
        savedValueStartsFromMonday = _startsFromMonday
    }

    fun hasScheduleWeeksSettingChanged(): Boolean = _scheduleWeeks != savedValueForScheduleWeeks

    fun hasWeekStartFromMondaySettingChanged(): Boolean = _startsFromMonday != savedValueStartsFromMonday

    fun setNbaTeam(context: Context, team: String) {
        _myNbaTeam = team
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putString(MY_NBA_TEAM_KEY, team)
            .apply()
    }

    fun setEuroSoccerTeam(context: Context, team: String) {
        _myEuroSoccerTeam = team
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putString(MY_EURO_SOCCER_TEAM_KEY, team)
            .apply()
    }

    fun setScheduleWeeks(context: Context, weeks: Int) {
        _scheduleWeeks = weeks
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putInt("scheduleWeeks", weeks)
            .apply()
    }

    fun setStartFromMonday(context: Context, startFromMonday: Boolean) {
        _startsFromMonday = startFromMonday
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putBoolean("weekStartsFromMonday", startFromMonday)
            .apply()
    }

    private const val MY_NBA_TEAM_KEY = "MyNbaTeam"

    private const val MY_EURO_SOCCER_TEAM_KEY = "MyEuroSoccerTeam"
}