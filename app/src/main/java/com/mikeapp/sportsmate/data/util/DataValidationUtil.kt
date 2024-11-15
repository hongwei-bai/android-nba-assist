package com.mikeapp.sportsmate.data.util

import com.mikeapp.sportsmate.AppConfigurations
import com.mikeapp.sportsmate.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.mikeapp.sportsmate.util.LocalDateTimeUtil
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getAheadOfHours
import java.util.*

object DataValidationUtil {
    fun dataMayOutdated(timeStamp: Long): Boolean = System.currentTimeMillis() - timeStamp >
            AppConfigurations.ForceRefreshInterval.FOR_SCHEDULE_HOUR * LocalDateTimeUtil.MILLIS_PER_HOUR

    fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
}