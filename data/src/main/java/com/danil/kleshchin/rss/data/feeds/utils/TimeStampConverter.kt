package com.danil.kleshchin.rss.data.feeds.utils

import java.text.SimpleDateFormat
import java.util.Locale

const val UNKNOWN_TIME = -1L
const val DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

fun getTimeStampFromDateTime(time: String): Long {
    return try {
        val timeFormat = SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault())
        timeFormat.parse(time)?.time ?: UNKNOWN_TIME
    } catch (e: Exception) {
        UNKNOWN_TIME
    }
}
