package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import java.util.*
import javax.inject.Inject

class WarriorsCalendarUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) {
    suspend fun getWarriorsSchedule(): List<MatchEvent> =
        nbaStatRepository.getTeamSchedule("gsw")
            .events
            .map {
                MatchEvent(
                    opponent = it.opponent.displayName,
                    isHome = it.opponent.home,
                    date = Calendar.getInstance().apply {
                        timeInMillis = it.unixTimeStamp
                    }
                )
            }
}