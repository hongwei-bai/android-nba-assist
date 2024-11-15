package com.mikeapp.sportsmate.alarm

import android.content.Context
import com.mikeapp.sportsmate.alarm.AlarmManager
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AlarmStarter {
    fun setAlarm(context: Context, dateStr: String, timeStr: String) {
        val ldt = LocalDateTime.of(LocalDate.parse(dateStr), LocalTime.parse(timeStr))
        val instant: Instant = ldt.atZone(ZoneId.systemDefault()).toInstant()
        AlarmManager().setAlarm(context, instant)
    }
}