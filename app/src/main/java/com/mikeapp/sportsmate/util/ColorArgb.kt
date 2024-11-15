package com.mikeapp.sportsmate.util


data class ColorArgb(
    var alpha: Long = 0xFF,
    var red: Long,
    var green: Long,
    var blue: Long
) {
    companion object {
        fun fromLong(color: Long?): ColorArgb? {
            if (color == null) {
                return null
            }
            val alpha = (color shr 24 and 0xff)
            val red = (color shr 16 and 0xff)
            val green = (color shr 8 and 0xff)
            val blue = (color and 0xff)
            return ColorArgb(alpha, red, green, blue)
        }
    }

    fun toLong(): Long = alpha and 0xff shl 24 or (red and 0xff shl 16) or (green and 0xff shl 8) or (blue and 0xff)
}