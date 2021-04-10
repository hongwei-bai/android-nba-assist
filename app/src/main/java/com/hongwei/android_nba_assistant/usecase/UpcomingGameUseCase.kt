package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import java.util.*
import javax.inject.Inject

class UpcomingGameUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun getUpcomingGame(): MatchEvent? =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .firstOrNull() { after(it.unixTimeStamp) }
            ?.run {
                MatchEvent(
                    opponentAbbrev = opponent.abbrev,
                    isHome = opponent.home,
                    location = opponent.location,
                    date = unixTimeStampToCalendar(unixTimeStamp)
                )
            }

    suspend fun getNumberOfGamesLeft(): Int =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .filter { after(it.unixTimeStamp) }
            .size

    private fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))
}