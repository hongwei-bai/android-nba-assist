package com.hongwei.android_nba_assist.usecase

import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.MatchEvent
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.MatchEvent.Companion.fromResponseModel
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
                fromResponseModel(it, nbaTeamRepository)
            }
}