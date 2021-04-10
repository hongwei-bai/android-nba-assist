package com.hongwei.android_nba_assistant.usecase

import android.util.Log
import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.unixTimeStampToCalendar
import javax.inject.Inject

class ForceRequestScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun forceRequestScheduleFromServer(): List<MatchEvent> =
        nbaStatRepository.requestTeamScheduleFromNetwork(localSettings.myTeam)
            .events
            .map {
                Log.d("bbbb", "forceRequestScheduleFromServer request end")
                MatchEvent(
                    opponentAbbrev = it.opponent.abbrev,
                    isHome = it.opponent.home,
                    location = it.opponent.location,
                    date = unixTimeStampToCalendar(it.unixTimeStamp)
                )
            }
}