package com.hami.sports_assist.util

import org.junit.Assert
import org.junit.Test

class ColorArgbTest {
    @Test
    fun `convert to ARGB then convert back to long should be the same`() {
        val colorLong = 0xFFFA0028
        val colorARGB = ColorArgb.fromLong(colorLong)
        val actualResult = colorARGB?.toLong()
        Assert.assertEquals(colorLong, actualResult)
    }
}