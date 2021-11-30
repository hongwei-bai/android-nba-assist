package com.hami.sports_assist.ui.theme

import android.util.Log
import com.hami.sports_assist.util.ColorArgb
import okhttp3.internal.toHexString

object ColorAdapter {
    fun adaptHomeColor(originalHomeColor: Long?): Long? {
        val colorHomeRgb = ColorArgb.fromLong(originalHomeColor) ?: return null
        debugColorAdaption(
            "bbbb", "colorHome, r/g/b: ${colorHomeRgb.red.toHexString()}/" +
                    "${colorHomeRgb.green.toHexString()}/${colorHomeRgb.blue.toHexString()}"
        )
        // Reduce red if it is too strong and dazzling.
        val colorHomeAdapted = if (colorHomeRgb.red > 0xE0 && colorHomeRgb.green < 0x10 && colorHomeRgb.blue < 0x40) {
            colorHomeRgb.apply { red = 0x90 }.toLong()
        } else {
            originalHomeColor
        }
        debugColorAdaption(
            "bbbb", "colorHome: ${originalHomeColor?.toHexString()?.uppercase()} " +
                    "-> ${colorHomeAdapted?.toHexString()?.uppercase()}"
        )
        return colorHomeAdapted
    }

    fun adaptGuestColor(originalGuestColor: Long?): Long? {
        val colorGuestRgb = ColorArgb.fromLong(originalGuestColor) ?: return null
        debugColorAdaption(
            "bbbb", "colorGuest, r/g/b: ${colorGuestRgb.red.toHexString()}/" +
                    "${colorGuestRgb.green.toHexString()}/${colorGuestRgb.blue.toHexString()}"
        )
        if (colorGuestRgb.red > 0xE0 && colorGuestRgb.green > 0xE0 && colorGuestRgb.blue > 0xD0) {
            colorGuestRgb.red -= 0x40
            colorGuestRgb.green -= 0x40
            colorGuestRgb.blue -= 0x40
        }
        val colorGuestAdapted = colorGuestRgb.toLong()
        debugColorAdaption(
            "bbbb", "colorGuest: ${originalGuestColor?.toHexString()?.uppercase()} " +
                    "-> ${colorGuestAdapted.toHexString().uppercase()}"
        )
        return colorGuestAdapted
    }

    private const val DEBUG_COLOR_ADAPTION = false

    private fun debugColorAdaption(tag: String, message: String) {
        if (DEBUG_COLOR_ADAPTION) {
            Log.d(tag, message)
        }
    }
}