package com.hongwei.android_nba_assist.usecase

import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getDateInWeeks
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getLastMondayForSunday
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getMondayOfWeek
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getSundayOfWeek
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.unixTimeStampToCalendar
import com.hongwei.android_nba_assist.viewmodel.viewobject.MatchEvent
import com.hongwei.android_nba_assist.viewmodel.viewobject.MatchEvent.Companion.fromResponseModel
import javax.inject.Inject

class TeamScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun getTeamSchedule(): List<MatchEvent> =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .filter {
                unixTimeStampToCalendar(it.unixTimeStamp).run {
                    if (localSettings.startsFromMonday) {
                        after(getLastMondayForSunday())
                                && before(getMondayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    } else {
                        after(getSundayOfWeek())
                                && before(getSundayOfWeek(getDateInWeeks(localSettings.scheduleWeeks)))
                    }
                }
            }
            .map {
                fromResponseModel(it, nbaTeamRepository)
            }
}