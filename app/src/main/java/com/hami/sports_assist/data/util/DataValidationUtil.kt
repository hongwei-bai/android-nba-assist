package com.hami.sports_assist.data.util

import com.hami.sports_assist.AppConfigurations
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hami.sports_assist.util.LocalDateTimeUtil
import com.hami.sports_assist.util.LocalDateTimeUtil.getAheadOfHours
import java.util.*

object DataValidationUtil {
    fun dataMayOutdated(timeStamp: Long): Boolean = System.currentTimeMillis() - timeStamp >
            AppConfigurations.ForceRefreshInterval.FOR_SCHEDULE_HOUR * LocalDateTimeUtil.MILLIS_PER_HOUR

    fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
}