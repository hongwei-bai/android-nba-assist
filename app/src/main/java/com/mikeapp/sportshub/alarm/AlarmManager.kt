package com.mikeapp.sportshub.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar


class AlarmManager {
    fun setAlarm(context: Context, time: Instant) {
        with(context) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, alarmIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Set the time for the alarm (replace with your preferred time and date)
            val calendar = Calendar.getInstance()
            val ldt = LocalDateTime.ofInstant(time, ZoneId.systemDefault())
            calendar.set(Calendar.HOUR_OF_DAY, ldt.hour)
            calendar.set(Calendar.MINUTE, ldt.minute)
            calendar.set(Calendar.SECOND, ldt.second)

            // Schedule the alarm
            try {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            } catch (e: SecurityException) {
                Log.e(TAG, "SecurityException: ${e.localizedMessage}")
            }
        }
    }

    companion object {
        private val TAG = AlarmManager::class.simpleName
    }
}