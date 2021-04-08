package com.hongwei.android_nba_assistant.util

import android.content.Context
import android.graphics.drawable.Drawable

object DrawableByNameUtil {
    fun getTeamDrawable(context: Context, teamShort: String): Drawable =
        getDrawableByName(context, teamShort)

    fun getTeamBannerDrawable(context: Context, teamShort: String): Drawable =
        getDrawableByName(context, "banner_$teamShort")

    private fun getDrawableByName(context: Context, name: String): Drawable =
        context.resources.getDrawable(
            context.resources.getIdentifier(
                name,
                "drawable",
                context.packageName
            )
        )
}