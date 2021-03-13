package com.danil.kleshchin.rss.data.feeds.utils

import java.text.SimpleDateFormat
import java.util.Locale

const val UNKNOWN_TIME = -1L

fun getTimeStampFromDateTime(time: String): Long {
    val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return timeFormat.parse(time)?.time ?: UNKNOWN_TIME
}
