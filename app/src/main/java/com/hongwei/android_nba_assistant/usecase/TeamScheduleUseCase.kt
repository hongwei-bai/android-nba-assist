package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.repository.NbaTeamRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getDateInWeeks
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getMondayOfWeek
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getSundayOfWeek
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getWeekAhead
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import java.util.*
import javax.inject.Inject

class TeamScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    fun getTeamSchedule(): List<MatchEvent> =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .filter {
                unixTimeStampToCalendar(it.unixTimeStamp).run {
                    if (localSettings.startsFromMonday) {
                        after(getMondayOfWeek(getWeekAhead(1)))
                                && before(getMondayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    } else {
                        after(getSundayOfWeek())
                                && before(getSundayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    }
                }
            }
            .map {
                val teamShort = it.opponent.abbrev.toLowerCase(Locale.US)
                MatchEvent(
                    opponentAbbrev = teamShort,
                    opponentLogoUrl = nbaTeamRepository.getTeamLogoUrl(teamShort),
                    opponentLogoPlaceholder = nbaTeamRepository.getTeamLogoPlaceholder(teamShort),
                    isHome = it.opponent.home,
                    location = it.opponent.location,
                    date = unixTimeStampToCalendar(it.unixTimeStamp)
                )
            }
}