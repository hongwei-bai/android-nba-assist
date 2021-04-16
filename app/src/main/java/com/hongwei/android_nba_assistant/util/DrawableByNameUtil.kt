package com.hongwei.android_nba_assistant.util

import android.content.Context
import android.graphics.drawable.Drawable

object DrawableByNameUtil {
    fun getDrawableByName(context: Context, name: String): Drawable =
        context.resources.getDrawable(
            context.resources.getIdentifier(
                name,
                "drawable",
                context.packageName
            )
        )
}