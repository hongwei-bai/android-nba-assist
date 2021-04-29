package com.hongwei.android_nba_assist.usecase

import com.hongwei.android_nba_assist.datasource.local.Conference
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.TeamStandingViewObject
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.TeamStandingViewObject.Companion.fromResponseModel
import javax.inject.Inject

class StandingUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings
) {
    suspend fun getStanding(): List<TeamStandingViewObject> =
        nbaStatRepository.getStandingFromLocal().run {
            when (localSettings.conference) {
                Conference.Eastern -> eastern
                Conference.Western -> western
            }.teams.map {
                fromResponseModel(it, nbaTeamRepository)
            }
        }
}