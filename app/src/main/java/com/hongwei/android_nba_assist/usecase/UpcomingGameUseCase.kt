package com.hongwei.android_nba_assist.usecase

import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.MatchEvent.Companion.fromResponseModel
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.MatchEvent
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
            .firstOrNull { after(it.unixTimeStamp) }
            ?.let {
                fromResponseModel(it, nbaTeamRepository)
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