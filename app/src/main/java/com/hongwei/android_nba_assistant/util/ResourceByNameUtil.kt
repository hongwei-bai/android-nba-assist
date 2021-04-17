package com.hongwei.android_nba_assistant.util

import android.content.Context
import android.graphics.drawable.Drawable

object ResourceByNameUtil {
    fun getDrawableByName(context: Context, name: String): Drawable =
        context.resources.getDrawable(
            context.resources.getIdentifier(
                name,
                "drawable",
                context.packageName
            )
        )

    fun getStyleByName(context: Context, name: String): Int =
        context.resources.getIdentifier(
            name,
            "style",
            context.packageName
        )
}