package com.hongwei.android_nba_assistant.usecase

import com.hongwei.android_nba_assistant.datasource.local.LocalSettings
import com.hongwei.android_nba_assistant.repository.NbaTeamRepository
import com.hongwei.android_nba_assistant.viewmodel.viewobject.TeamTheme
import javax.inject.Inject

class TeamThemeUseCase @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    fun getTeamTheme(): TeamTheme = TeamTheme(
        nbaTeamRepository.getTeamBannerUrl(localSettings.myTeam)
    )
}