package com.mikeapp.sportsmate.data.local

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mikeapp.sportsmate.R
import com.mikeapp.sportsmate.AppConfigurations.Network.DEFAULT_LOGO_EXTENSION
import com.mikeapp.sportsmate.AppConfigurations.Network.DEFAULT_LOGO_WIDTH
import com.mikeapp.sportsmate.AppConfigurations.Network.NBA_LOGO_PATH
import com.mikeapp.sportsmate.AppConfigurations.Network.PLACEHOLDER_WIDTH
import com.mikeapp.sportsmate.util.ResourceByNameUtil.getDrawableByName
import com.mikeapp.sportsmate.util.ResourceByNameUtil.getResourceIdByName

object ConstantEndpoint {
    fun getDefaultTeamBanner(context: Context, team: String): Drawable =
        ContextCompat.getDrawable(context, R.drawable.banner_placeholder)!!

    fun getTeamLogoUrl(team: String): String = "$NBA_LOGO_PATH$team$DEFAULT_LOGO_EXTENSION"
        .replace(PLACEHOLDER_WIDTH, DEFAULT_LOGO_WIDTH)

    fun getDefaultTeamLogoResId(context: Context, team: String): Int = getResourceIdByName(context, team)

    fun getDefaultTeamLogo(context: Context, team: String): Drawable = getDrawableByName(context, team)
}