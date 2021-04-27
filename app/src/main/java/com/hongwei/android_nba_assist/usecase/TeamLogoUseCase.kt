package com.hongwei.android_nba_assist.usecase

import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import javax.inject.Inject

class TeamLogoUseCase @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository
) {
    fun getTeamLogoUrl(team: String): String = nbaTeamRepository.getTeamLogoUrl(team)

    fun getTeamLogoPlaceholder(team: String): Drawable = nbaTeamRepository.getTeamLogoPlaceholder(team)
}