package com.hongwei.android_nba_assist.repository

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.material.Colors
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_BANNER_EXTENSION
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_BANNER_WIDTH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_LOGO_EXTENSION
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_LOGO_WIDTH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.NBA_BANNER_PATH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.NBA_LOGO_PATH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.PLACEHOLDER_WIDTH
import com.hongwei.android_nba_assist.datasource.network.service.NbaThemeService
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getDrawableByName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val nbaThemeService: NbaThemeService
) {
    fun getTeamBannerUrl(team: String): String =
        "${NBA_BANNER_PATH.replace(PLACEHOLDER_WIDTH, DEFAULT_BANNER_WIDTH)}$team$DEFAULT_BANNER_EXTENSION"

    fun getTeamLogoUrl(team: String): String =
        "${NBA_LOGO_PATH.replace(PLACEHOLDER_WIDTH, DEFAULT_LOGO_WIDTH)}$team$DEFAULT_LOGO_EXTENSION"

    fun getTeamLogoPlaceholder(team: String): Drawable = getDrawableByName(context, team)

    suspend fun getTeamColorPalette(team: String): Colors {
        val response = nbaThemeService.getTeamTheme(team, -1)
        
    }
}