package com.hongwei.android_nba_assist.datasource.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlin.properties.Delegates

object AppSettings {
    private lateinit var _myTeam: String

    private var _scheduleWeeks by Delegates.notNull<Int>()

    private var _startsFromMonday by Delegates.notNull<Boolean>()

    private var savedValueForScheduleWeeks by Delegates.notNull<Int>()

    private var savedValueStartsFromMonday by Delegates.notNull<Boolean>()

    val myTeam: String
        get() = _myTeam

    val scheduleWeeks: Int
        get() = _scheduleWeeks

    val weekStartsFromMonday: Boolean
        get() = _startsFromMonday

    fun initialize(context: Context) {
        _myTeam = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString("MyTeam", "gs") ?: "gs"
        _scheduleWeeks = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getInt("scheduleWeeks", 2)
        _startsFromMonday = context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getBoolean("weekStartsFromMonday", true)
        savedValueForScheduleWeeks = _scheduleWeeks
        savedValueStartsFromMonday = _startsFromMonday
    }

    fun hasScheduleWeeksSettingChanged(): Boolean = _scheduleWeeks != savedValueForScheduleWeeks

    fun hasWeekStartFromMondaySettingChanged(): Boolean = _startsFromMonday != savedValueStartsFromMonday

    fun setTeam(context: Context, team: String) {
        _myTeam = team
        context.getSharedPreferences("AppSettings", MODE_PRIVATE)
            .edit()
            .putString("MyTeam", team)
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
}