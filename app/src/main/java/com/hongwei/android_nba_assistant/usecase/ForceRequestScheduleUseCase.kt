package com.hongwei.android_nba_assistant.usecase

import android.util.Log
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.repository.NbaTeamRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import java.util.*
import javax.inject.Inject

class ForceRequestScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun forceRequestScheduleFromServer(): List<MatchEvent> =
        nbaStatRepository.requestTeamScheduleFromNetwork(localSettings.myTeam)
            .events
            .map {
                Log.d("bbbb", "forceRequestScheduleFromServer request end")
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