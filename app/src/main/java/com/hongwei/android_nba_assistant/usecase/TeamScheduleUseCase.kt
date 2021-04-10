package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getDateInWeeks
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getMondayOfWeek
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getSundayOfWeek
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import javax.inject.Inject

class TeamScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun getTeamSchedule(): List<MatchEvent> =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .filter {
                unixTimeStampToCalendar(it.unixTimeStamp).run {
                    if (localSettings.startsFromMonday) {
                        after(getMondayOfWeek())
                                && before(getMondayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    } else {
                        after(getSundayOfWeek())
                                && before(getSundayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    }
                }
            }
            .map {
                MatchEvent(
                    opponentAbbrev = it.opponent.abbrev,
                    isHome = it.opponent.home,
                    location = it.opponent.location,
                    date = unixTimeStampToCalendar(it.unixTimeStamp)
                )
            }
}