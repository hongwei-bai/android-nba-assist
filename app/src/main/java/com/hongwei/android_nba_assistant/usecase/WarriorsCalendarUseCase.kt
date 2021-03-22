package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.constant.AppConfigurations.Date.UTC_DATE_FORMAT
import com.hongwei.android_nba_assistant.repository.NbaStatRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class WarriorsCalendarUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) {
    suspend fun getWarriorsSchedule(): List<MatchEvent> =
        nbaStatRepository.getTeamSchedule("gs")
            .events
            .pre.first()
            .group
            .map {
                MatchEvent(
                    opponent = it.opponent.displayName,
                    isHome = it.opponent.homeAwaySymbol == "@",
                    date = Calendar.getInstance().apply {
                        time = SimpleDateFormat(UTC_DATE_FORMAT, Locale.ENGLISH).parse(it.date.date)
                    }
                )
            }
}