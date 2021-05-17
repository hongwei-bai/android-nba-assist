package com.hongwei.android_nba_assist.datasource.local

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_LOGO_EXTENSION
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.DEFAULT_LOGO_WIDTH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.NBA_LOGO_PATH
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.PLACEHOLDER_WIDTH
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getDrawableByName
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getResourceIdByName

object FixEndpoint {
    fun getDefaultTeamBanner(context: Context, team: String): Drawable =
        ContextCompat.getDrawable(context, R.drawable.banner_placeholder)!!

    fun getTeamLogoUrl(team: String): String = "$NBA_LOGO_PATH$team$DEFAULT_LOGO_EXTENSION"
        .replace(PLACEHOLDER_WIDTH, DEFAULT_LOGO_WIDTH)

    fun getDefaultTeamLogoResId(context: Context, team: String): Int = getResourceIdByName(context, team)

    fun getDefaultTeamLogo(context: Context, team: String): Drawable = getDrawableByName(context, team)
}