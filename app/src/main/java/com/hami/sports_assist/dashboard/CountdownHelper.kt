package com.hami.sports_assist.dashboard

import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.COUNTDOWN_ZERO_FREEZE_MILLIS
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.DISPLAY_COUNTDOWN_IN_HOURS
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.DISPLAY_HOURS_IN_HOURS
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.DISPLAY_STARTED_FROM_MINUTES
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hami.sports_assist.util.LocalDateTimeUtil.MILLIS_PER_HOUR
import com.hami.sports_assist.util.LocalDateTimeUtil.MILLIS_PER_MINUTE

object CountdownHelper {
    fun getUpcomingRange(
        eventTime: Long,
        millisDiff: Long = eventTime - System.currentTimeMillis()
    ): UpcomingRange = when (millisDiff) {
        in 0..DISPLAY_COUNTDOWN_IN_HOURS * MILLIS_PER_HOUR -> UpcomingRange.CountingDown
        in DISPLAY_COUNTDOWN_IN_HOURS * MILLIS_PER_HOUR..DISPLAY_HOURS_IN_HOURS * MILLIS_PER_HOUR -> UpcomingRange.InHours
        in -COUNTDOWN_ZERO_FREEZE_MILLIS until 0 -> UpcomingRange.Now
        in -DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE until -COUNTDOWN_ZERO_FREEZE_MILLIS -> UpcomingRange.CountingUp
        in -IGNORE_TODAY_S_GAME_FROM_HOURS * MILLIS_PER_HOUR until -DISPLAY_STARTED_FROM_MINUTES * MILLIS_PER_MINUTE -> UpcomingRange.Started
        else -> UpcomingRange.Passed
    }
}

enum class UpcomingRange {
    InHours, CountingDown, Now, CountingUp, Started, Passed
}
