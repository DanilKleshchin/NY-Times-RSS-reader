package com.danil.kleshchin.rss.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val UNKNOWN_TIME_STRING = "error"

const val oneHourSeconds = 3600L
const val oneDaySeconds = 86_400L
const val oneWeekSeconds = 604_800L
const val oneMonthSeconds = 2_592_000L
const val oneYearSeconds = 31_104_000L

const val secondsInAlmostOneHours = 3599L
const val secondsInAlmost24Hours = 86_399L
const val secondsInAlmost7Days = 604_799L
const val secondsInAlmost4Weeks = 2_419_199L
const val secondsInAlmost12Months = 31_103_999L

fun getDateTimeFromTimeStamp(timeStamp: Long): String {
    val timeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return timeFormat.format(Date(timeStamp))
}

/**
 * Returns a string with the elapsed time like: Hour ago, 2 days ago etc.
 */
fun getElapsedTimeFromCurrentTime(timeStamp: Long, currentTimeMillis: Long): String {
    val differenceSeconds = (currentTimeMillis - timeStamp) / 1000
    if (differenceSeconds < 0) {
        return UNKNOWN_TIME_STRING
    }

    when (differenceSeconds) {
        in 0..secondsInAlmostOneHours -> {
            return getMinutesElapsedTime(differenceSeconds)
        }
        in oneHourSeconds..secondsInAlmost24Hours -> {
            return getHourlyElapsedTime(differenceSeconds)
        }
        in oneDaySeconds..secondsInAlmost7Days -> {
            return getDailyElapsedTime(differenceSeconds)
        }
        in oneWeekSeconds..secondsInAlmost4Weeks -> {
            return getWeeklyElapsedTime(differenceSeconds)
        }
        in oneMonthSeconds..secondsInAlmost12Months -> {
            return getMonthlyElapsedTime(differenceSeconds)
        }
        else -> {
            return getYearlyElapsedTime(differenceSeconds)
        }
    }
}

fun getMinutesElapsedTime(timeSeconds: Long): String {
    when (timeSeconds) {
        in 0..599 -> { //between 0 and 15 minutes
            return "Moments ago"
        }
        in 900..1799 -> { //between 15 and 30 minutes
            return "15 minutes ago"
        }
        in 1800..3599 -> { //between 30 and 60 minutes(hour)
            return "30 minutes ago"
        }
    }
    return UNKNOWN_TIME_STRING
}

fun getHourlyElapsedTime(timeSeconds: Long): String {
    val oneHour = "Hour ago"
    val severalHours = "hours ago"
    return getElapsedTimeString(timeSeconds, oneHourSeconds, oneHour, severalHours)
}

fun getDailyElapsedTime(timeSeconds: Long): String {
    val oneDay = "Day ago"
    val severalDays = "days ago"
    return getElapsedTimeString(timeSeconds, oneDaySeconds, oneDay, severalDays)
}

fun getWeeklyElapsedTime(timeSeconds: Long): String {
    val oneWeek = "Week ago"
    val severalWeeks = "weeks ago"
    return getElapsedTimeString(timeSeconds, oneWeekSeconds, oneWeek, severalWeeks)
}

fun getMonthlyElapsedTime(timeSeconds: Long): String {
    val oneMonth = "Month ago"
    val severalMonth = "months ago"
    return getElapsedTimeString(timeSeconds, oneMonthSeconds, oneMonth, severalMonth)
}

fun getYearlyElapsedTime(timeSeconds: Long): String {
    val oneYear = "Year ago"
    val severalYear = "years ago"
    return getElapsedTimeString(timeSeconds, oneYearSeconds, oneYear, severalYear)
}

fun getElapsedTimeString(
    timeSeconds: Long,
    oneItemSeconds: Long,
    elapsedOne: String,
    elapsedPlural: String
): String {
    val oneItem = 1L // count of one hour, day, week, month
    val item = timeSeconds / oneItemSeconds
    if (item == oneItem) { //TODO replace with plurals
        return elapsedOne
    }
    return "$item $elapsedPlural"
}
