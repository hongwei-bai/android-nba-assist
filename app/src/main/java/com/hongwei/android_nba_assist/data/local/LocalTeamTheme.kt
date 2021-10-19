package com.hongwei.android_nba_assist.data.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_BANNER_EXTENSION
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_BANNER_WIDTH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.NBA_BANNER_PATH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.PLACEHOLDER_WIDTH
import com.hongwei.android_nba_assist.data.room.TeamThemeEntity
import okhttp3.internal.toHexString
import javax.inject.Inject

class LocalTeamTheme @Inject constructor() {
    companion object {
        object LakersColors {
            val Light = Color(0xFFFFFFFF)
            val Home = Color(0xFF542583)
            val Guest = Color(0xFFFDB827)
        }

        object WarriorsColors {
            val Light = Color(0xFFFFFFFF)
            val Home = Color(0xFF1C4189)
            val Guest = Color(0xFFFCB826)
        }
    }

    fun getTeamTheme(team: String): TeamThemeEntity = when (team) {
        "gs" -> TeamThemeEntity(
            team = team, bannerUrl = bannerUrl(team),
            colorLight = WarriorsColors.Light.toLong(),
            colorHome = WarriorsColors.Home.toLong(),
            colorGuest = WarriorsColors.Guest.toLong()
        )
        "lal" -> TeamThemeEntity(
            team = team, bannerUrl = bannerUrl(team),
            colorLight = LakersColors.Light.toLong(),
            colorHome = LakersColors.Home.toLong(),
            colorGuest = LakersColors.Guest.toLong()
        )
        else -> TeamThemeEntity(
            team = team, bannerUrl = bannerUrl(team),
            colorLight = WarriorsColors.Light.toLong(),
            colorHome = WarriorsColors.Home.toLong(),
            colorGuest = WarriorsColors.Guest.toLong()
        )
    }

    private fun bannerUrl(team: String): String = "$NBA_BANNER_PATH$team$DEFAULT_BANNER_EXTENSION"
        .replace(PLACEHOLDER_WIDTH, DEFAULT_BANNER_WIDTH)

    private fun Color.toLong(): Long = toArgb().toHexString().toLongOrNull(16) ?: 0xFFFFFFFF
}