package com.danil.kleshchin.rss.domain

import com.danil.kleshchin.rss.domain.entity.Section
import org.junit.Test

import org.junit.Assert.*

class SectionTest {

    @Test
    fun `test Section enum with quotes in name`() {
        val expected = "Sunday-Review"
        val actual = Section.`Sunday-Review`.name
        assertEquals(expected, actual)
    }
}
