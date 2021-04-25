package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.repository.NbaTeamRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import java.util.*
import javax.inject.Inject

class UpcomingGameUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun getUpcomingGame(): MatchEvent? =
        nbaStatRepository.getTeamScheduleFromLocal(localSettings.myTeam)
            .events
            .firstOrNull() { after(it.unixTimeStamp) }
            ?.run {
                val teamShort = opponent.abbrev.toLowerCase(Locale.US)
                MatchEvent(
                    opponentAbbrev = teamShort,
                    opponentLogoUrl = nbaTeamRepository.getTeamLogoUrl(teamShort),
                    opponentLogoPlaceholder = nbaTeamRepository.getTeamLogoPlaceholder(teamShort),
                    isHome = opponent.home,
                    location = opponent.location,
                    date = unixTimeStampToCalendar(unixTimeStamp),
                    result = Result.fromResponseResult(result)
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