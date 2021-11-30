package com.hongwei.android_nba_assist.data.util

import com.hongwei.android_nba_assist.AppConfigurations
import com.hongwei.android_nba_assist.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import java.util.*

object DataValidationUtil {
    fun dataMayOutdated(timeStamp: Long): Boolean = System.currentTimeMillis() - timeStamp >
            AppConfigurations.ForceRefreshInterval.FOR_SCHEDULE_HOUR * LocalDateTimeUtil.MILLIS_PER_HOUR

    fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
}