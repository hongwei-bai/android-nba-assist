package com.hongwei.android_nba_assist.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.TypedValue

object ResourceByNameUtil {
    fun getColorFromAttribute(context: Context, resId: Int): Int {
        val typedValue = TypedValue()
        val textSizeAttr = intArrayOf(resId)
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, textSizeAttr)
        val color = a.getDimensionPixelSize(0, -1)
        a.recycle()
        return color
    }

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