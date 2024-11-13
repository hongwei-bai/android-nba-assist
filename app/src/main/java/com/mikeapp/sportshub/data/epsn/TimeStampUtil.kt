package com.mikeapp.sportshub.data.epsn

import android.annotation.SuppressLint
import com.mikeapp.sportshub.data.epsn.TimeStampUtil.Companion.TimeZoneString.SYDNEY
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeStampUtil {
    @SuppressLint("DefaultLocale")
    fun getTimeVersionWithMinute(timeZone: String = SYDNEY): Long {
        val sydneyTime = Clock.System.now().toLocalDateTime(TimeZone.of(timeZone))
        val formattedDateTime = String.format(
            "%04d%02d%02d%02d%02d%02d",
            sydneyTime.date.year,
            sydneyTime.date.monthNumber,
            sydneyTime.date.dayOfMonth,
            sydneyTime.time.hour,
            sydneyTime.time.minute,
            sydneyTime.time.second
        )
        return formattedDateTime.toLong()
    }

    companion object {
        object TimeZoneString {
            const val UTC = "UTC"
            const val SYDNEY = "Australia/Sydney"
        }
    }
}