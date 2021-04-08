package com.hongwei.android_nba_assistant.util

import org.jetbrains.annotations.TestOnly
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
import kotlin.math.roundToInt

object LocalDateTimeUtil {
    private const val MILLIS_PER_DAY = 1000 * 3600 * 24
    private const val MILLIS_PER_HOUR = 1000 * 3600 * 1.00

    fun getLocalDateDisplay(calendar: Calendar, format: String = "EEE., MMM.d"): String =
        SimpleDateFormat(format, Locale.US).format(calendar.time).toUpperCase(Locale.US)

    fun getLocalTimeDisplay(calendar: Calendar, format: String = "H:mm a"): String =
        SimpleDateFormat(format, Locale.US).format(calendar.time)

    fun getInHours(calendar: Calendar): Int = getInDays(calendar, getInstance())

    @TestOnly
    fun getInHours(calendar: Calendar, reference: Calendar): Int =
        ((calendar.timeInMillis - reference.timeInMillis) / MILLIS_PER_HOUR).roundToInt()

    fun getInDays(calendar: Calendar): Int = getInDays(calendar, getInstance())

    @TestOnly
    fun getInDays(calendar: Calendar, reference: Calendar): Int =
        ((getBeginOfDay(calendar).timeInMillis - getBeginOfDay(reference).timeInMillis) / MILLIS_PER_DAY).toInt()

    fun getBeginOfDay(calendar: Calendar): Calendar {
        val newCalendar: Calendar = calendar.clone() as Calendar
        newCalendar.set(HOUR_OF_DAY, 0)
        newCalendar.set(MINUTE, 0)
        newCalendar.set(SECOND, 0)
        newCalendar.set(MILLISECOND, 0)
        return newCalendar
    }

    fun getEndOfDay(calendar: Calendar): Calendar {
        val newCalendar: Calendar = calendar.clone() as Calendar
        newCalendar.set(HOUR_OF_DAY, 23)
        newCalendar.set(MINUTE, 59)
        newCalendar.set(SECOND, 59)
        newCalendar.set(MILLISECOND, 999)
        return newCalendar
    }

    fun getMondayOfWeek(calendar: Calendar): Calendar {
        val newCalendar: Calendar = calendar.clone() as Calendar
        newCalendar.set(DAY_OF_WEEK, MONDAY)
        newCalendar.set(HOUR_OF_DAY, 0)
        newCalendar.set(MINUTE, 0)
        newCalendar.set(SECOND, 0)
        newCalendar.set(MILLISECOND, 0)
        return newCalendar
    }
}