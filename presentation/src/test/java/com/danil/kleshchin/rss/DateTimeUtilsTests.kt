package com.danil.kleshchin.rss

import com.danil.kleshchin.rss.utils.getDailyElapsedTime
import com.danil.kleshchin.rss.utils.getDateTimeFromTimeStamp
import com.danil.kleshchin.rss.utils.getElapsedTimeFromCurrentTime
import com.danil.kleshchin.rss.utils.getHourlyElapsedTime
import com.danil.kleshchin.rss.utils.getMonthlyElapsedTime
import com.danil.kleshchin.rss.utils.getTimeStampFromDateTime
import com.danil.kleshchin.rss.utils.getWeeklyElapsedTime
import com.danil.kleshchin.rss.utils.getYearlyElapsedTime
import org.junit.Assert.assertEquals
import org.junit.Test

class DateTimeUtilsTests {

    @Test
    fun time_stamp_converting_test() {
        val time = "2021-02-25T06:00:08-05:00"
        val expectTime = "25/02/2021 06:00:08"
        val timeStamp = getTimeStampFromDateTime(time)
        assertEquals(expectTime, getDateTimeFromTimeStamp(timeStamp))
    }

    @Test
    fun elapsed_time_test() {
        val time = "2021-03-03T22:13:08"
        val timeStamp = getTimeStampFromDateTime(time)

        val currentTime = getTimeStampFromDateTime("2021-03-04T22:13:08")
        val expect = "Day ago"

        val currentTime23 = getTimeStampFromDateTime("2021-03-04T22:13:05")
        val expect23 = "23 hours ago"

        assertEquals(expect, getElapsedTimeFromCurrentTime(timeStamp, currentTime))
        assertEquals(expect23, getElapsedTimeFromCurrentTime(timeStamp, currentTime23))
    }

    @Test
    fun elapsed_hours_test() {
        val timeHour = 3600L
        val expectHour = "Hour ago"
        val time2Hours = 7200L
        val expect2Hours = "2 hours ago"
        val time23Hours = 86399L // almost 24 hours (1 day)
        val expect23Hours = "23 hours ago"
        assertEquals(expectHour, getHourlyElapsedTime(timeHour))
        assertEquals(expect2Hours, getHourlyElapsedTime(time2Hours))
        assertEquals(expect23Hours, getHourlyElapsedTime(time23Hours))
    }

    @Test
    fun elapsed_days_test() {
        val timeDay = 86_400L
        val expectDay = "Day ago"
        val time6Days = 604_799L // almost 7 days (1 week)
        val expect6Days = "6 days ago"
        assertEquals(expectDay, getDailyElapsedTime(timeDay))
        assertEquals(expect6Days, getDailyElapsedTime(time6Days))
    }

    @Test
    fun elapsed_weeks_test() {
        val timeWeek = 604_800L
        val expectWeek = "Week ago"
        val time2Weeks = 1_209_600L
        val expect2Weeks = "2 weeks ago"
        val time3Weeks = 2_419_199L // almost 4 weeks (1 month)
        val expect3Weeks = "3 weeks ago"
        assertEquals(expectWeek, getWeeklyElapsedTime(timeWeek))
        assertEquals(expect2Weeks, getWeeklyElapsedTime(time2Weeks))
        assertEquals(expect3Weeks, getWeeklyElapsedTime(time3Weeks))
    }

    @Test
    fun elapsed_month_test() {
        val timeMonth = 2_592_000L
        val expectMonth = "Month ago"
        val time2Months = 5_184_000L
        val expect2Months = "2 months ago"
        val time11Month = 31_103_999L // almost 12 months (1 year)
        val expect11Month = "11 months ago"
        assertEquals(expectMonth, getMonthlyElapsedTime(timeMonth))
        assertEquals(expect2Months, getMonthlyElapsedTime(time2Months))
        assertEquals(expect11Month, getMonthlyElapsedTime(time11Month))
    }

    @Test
    fun elapsed_years_test() {
        val timeYear = 31_104_000L
        val expectYear = "Year ago"
        val time5Years = 157_784_760L
        val expect5Years = "5 years ago"
        assertEquals(expectYear, getYearlyElapsedTime(timeYear))
        assertEquals(expect5Years, getYearlyElapsedTime(time5Years))
    }
}
