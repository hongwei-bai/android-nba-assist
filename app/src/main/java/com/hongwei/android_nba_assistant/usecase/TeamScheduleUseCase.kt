package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import java.util.*
import javax.inject.Inject

class TeamScheduleUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) {
    suspend fun getTeamSchedule(team: String): List<MatchEvent> =
        nbaStatRepository.getTeamSchedule(team)
            .events
            .map {
                MatchEvent(
                    opponent = it.opponent.displayName,
                    opponentLogo = it.opponent.logo,
                    isHome = it.opponent.home,
                    date = Calendar.getInstance().apply {
                        timeInMillis = it.unixTimeStamp
                        //debug
//                        set(2021, Calendar.APRIL, 9,
//                            11, 45, 0)
                    }
                )
            }
}