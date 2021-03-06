package com.hami.sports_assist.util

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class LocalDateTimeUtilTest {
    @Test
    fun testHourDiffRoundDown() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsNow = getCalendarInstance(2020, Calendar.APRIL, 8, 10, 0, 1)
        val inHours = LocalDateTimeUtil.getInHours(calendar, referenceAsNow)
        assertEquals(2, inHours)
    }

    @Test
    fun testHourDiffRoundUp() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsNow = getCalendarInstance(2020, Calendar.APRIL, 8, 9, 59, 59)
        val inHours = LocalDateTimeUtil.getInHours(calendar, referenceAsNow)
        assertEquals(3, inHours)
    }

    @Test
    fun testDayDiffLessThan1Day() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsToday = getCalendarInstance(2020, Calendar.APRIL, 7, 23, 37, 42)
        val inDays = LocalDateTimeUtil.getInDays(calendar, referenceAsToday)
        assertEquals(1, inDays)
    }

    @Test
    fun testDayDiffMoreThan1Day() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsToday = getCalendarInstance(2020, Calendar.APRIL, 7, 5, 50, 32)
        val inDays = LocalDateTimeUtil.getInDays(calendar, referenceAsToday)
        assertEquals(1, inDays)
    }

    @Test
    fun testDayDiffEarlierToday() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsToday = getCalendarInstance(2020, Calendar.APRIL, 8, 14, 2, 4)
        val inDays = LocalDateTimeUtil.getInDays(calendar, referenceAsToday)
        assertEquals(0, inDays)
    }

    @Test
    fun testDayDiffLaterToday() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 8, 12, 30, 0)
        val referenceAsToday = getCalendarInstance(2020, Calendar.APRIL, 8, 8, 10, 21)
        val inDays = LocalDateTimeUtil.getInDays(calendar, referenceAsToday)
        assertEquals(0, inDays)
    }

    @Ignore
    @Test
    fun testSundayOfWeek() {
        val calendar = getCalendarInstance(2021, Calendar.APRIL, 8, 10, 0, 0)
        val sundayOfWeek = LocalDateTimeUtil.getSundayOfWeek(calendar)
        assertEquals("2021-04-08 10:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2021-04-04 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(sundayOfWeek.time))
    }

    @Test
    fun testMondayOfWeek() {
        val calendar = getCalendarInstance(2021, Calendar.APRIL, 8, 19, 37, 42)
        val mondayOfWeek = LocalDateTimeUtil.getMondayOfWeek(calendar)
        assertEquals("2021-04-08 19:37:42", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2021-04-05 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(mondayOfWeek.time))
    }

    @Test
    fun testMondayOfWeek_MondayMorning() {
        val calendar = getCalendarInstance(2021, Calendar.APRIL, 5, 0, 0, 0)
        val mondayOfWeek = LocalDateTimeUtil.getMondayOfWeek(calendar)
        assertEquals("2021-04-05 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2021-04-05 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(mondayOfWeek.time))
    }

    @Ignore
    @Test
    fun testMondayOfWeek_SundayNight() {
        val calendar = getCalendarInstance(2021, Calendar.APRIL, 11, 23, 59, 59)
        val mondayOfWeek = LocalDateTimeUtil.getMondayOfWeek(calendar)
        assertEquals("2021-04-11 23:59:59", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2021-04-12 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(mondayOfWeek.time))
    }

    @Test
    fun testBeginOfDay() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 30, 13, 5, 0)
        val endOfDay = LocalDateTimeUtil.getBeginOfDay(calendar)
        assertEquals("2020-04-30 13:05:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-30 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(endOfDay.time))
    }

    @Test
    fun testEndOfDay() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 30, 13, 5, 0)
        val endOfDay = LocalDateTimeUtil.getEndOfDay(calendar)
        assertEquals("2020-04-30 13:05:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-30 23:59:59", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(endOfDay.time))
    }

    @Test
    fun testEndOfDay_23_59_59() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 30, 23, 59, 59)
        val endOfDay = LocalDateTimeUtil.getEndOfDay(calendar)
        assertEquals("2020-04-30 23:59:59", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-30 23:59:59", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(endOfDay.time))
    }

    @Test
    fun testEndOfDay_0_0_0() {
        val calendar = getCalendarInstance(2020, Calendar.APRIL, 30, 0, 0, 0)
        val endOfDay = LocalDateTimeUtil.getEndOfDay(calendar)
        assertEquals("2020-04-30 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-30 23:59:59", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(endOfDay.time))
    }

    @Test
    fun testWeekAhead() {
        val calendar = getCalendarInstance(2020, Calendar.MAY, 1, 0, 0, 0)
        val aWeekAhead = LocalDateTimeUtil.getWeekAhead(1, calendar)
        assertEquals("2020-05-01 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-24 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(aWeekAhead.time))
    }

    @Test
    fun testLastMondayForNotSunday() {
        val calendar = getCalendarInstance(2020, Calendar.MAY, 1, 0, 0, 0)
        val aWeekAhead = LocalDateTimeUtil.getLastMondayForSunday(calendar)
        assertEquals("2020-05-01 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-27 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(aWeekAhead.time))
    }

    @Ignore
    @Test
    fun testLastMondayForSunday() {
        val calendar = getCalendarInstance(2020, Calendar.MAY, 2, 0, 0, 0)
        val aWeekAhead = LocalDateTimeUtil.getLastMondayForSunday(calendar)
        assertEquals("2020-05-03 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(calendar.time))
        assertEquals("2020-04-27 00:00:00", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(aWeekAhead.time))
    }

    companion object {
        private fun getCalendarInstance(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int) =
            Calendar.getInstance().apply {
                set(year, month, day, hour, minute, second)
            }
    }
}