package com.hongwei.android_nba_assist.compat.usecase

import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.TeamTheme
import javax.inject.Inject

class TeamThemeUseCase @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val localSettings: LocalSettings,
) {
    fun getTeamTheme(): TeamTheme = TeamTheme(
        nbaTeamRepository.getTeamBannerUrl(localSettings.myTeam)
    )
}