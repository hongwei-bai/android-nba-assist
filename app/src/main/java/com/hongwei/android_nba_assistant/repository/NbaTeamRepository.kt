package com.hongwei.android_nba_assistant.repository

import android.content.Context
import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.DEFAULT_BANNER_EXTENSION
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.DEFAULT_BANNER_WIDTH
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.DEFAULT_LOGO_EXTENSION
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.NBA_BANNER_PATH
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.NBA_LOGO_PATH
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.PLACEHOLDER_WIDTH
import com.hongwei.android_nba_assistant.util.ResourceByNameUtil.getDrawableByName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getTeamBannerUrl(team: String): String =
        "${NBA_BANNER_PATH.replace(PLACEHOLDER_WIDTH, DEFAULT_BANNER_WIDTH)}$team$DEFAULT_BANNER_EXTENSION"

    fun getTeamLogoUrl(team: String): String =
        "${NBA_LOGO_PATH.replace(PLACEHOLDER_WIDTH, DEFAULT_BANNER_WIDTH)}$team$DEFAULT_LOGO_EXTENSION"

    fun getTeamLogoPlaceholder(team: String): Drawable = getDrawableByName(context, team)
}