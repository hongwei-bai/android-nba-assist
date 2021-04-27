package com.hongwei.android_nba_assist.usecase

import com.hongwei.android_nba_assist.datasource.local.Conference
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.viewmodel.viewobject.TeamStandingViewObject
import javax.inject.Inject

class ForceRequestStandingUseCase @Inject constructor(
    private val nbaStatRepository: NbaStatRepository,
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    suspend fun forceRequestStandingFromServer(): List<TeamStandingViewObject> =
        nbaStatRepository.requestStandingFromNetwork().run {
            when (localSettings.conference) {
                Conference.Eastern -> eastern
                Conference.Western -> western
            }.teams.map {
                TeamStandingViewObject.fromResponseModel(it, nbaTeamRepository)
            }
        }
}