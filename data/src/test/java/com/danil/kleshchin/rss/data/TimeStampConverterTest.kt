package com.danil.kleshchin.rss.data

import com.danil.kleshchin.rss.data.feeds.utils.UNKNOWN_TIME
import com.danil.kleshchin.rss.data.feeds.utils.getTimeStampFromDateTime
import org.junit.Assert
import org.junit.Test

const val SOME_STRING = "SOME STRING"
const val TIME_LONG = 1617128730000L
const val TIME_STRING_PATTERN_1 = "2021-03-30T21:25:30"
const val TIME_STRING_PATTERN_2 = "30-03-2021 21:25:30"

class TimeStampConverterTest {

    @Test
    fun random_time_string_test() {
        val actual = getTimeStampFromDateTime(SOME_STRING)
        Assert.assertEquals(UNKNOWN_TIME, actual)
    }

    @Test
    fun empty_time_test() {
        val actual = getTimeStampFromDateTime("")
        Assert.assertEquals(UNKNOWN_TIME, actual)
    }

    @Test
    fun correct_time_test() {
        val actual = getTimeStampFromDateTime(TIME_STRING_PATTERN_1)
        Assert.assertEquals(TIME_LONG, actual)
    }

    @Test
    fun incorrect_time_format() {
        val actual = getTimeStampFromDateTime(TIME_STRING_PATTERN_2)
        Assert.assertEquals(UNKNOWN_TIME, actual)
    }
}
