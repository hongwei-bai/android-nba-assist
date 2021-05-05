package com.hongwei.android_nba_assist.datasource.local

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getDrawableByName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FixEndpoint @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getDefaultTeamLogo(team: String): Drawable = getDrawableByName(context, team)

    fun getDefaultTeamBanner(team: String): Drawable =
        ContextCompat.getDrawable(context, R.drawable.banner_placeholder)!!
}