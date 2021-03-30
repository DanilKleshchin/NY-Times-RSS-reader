package com.danil.kleshchin.rss.domain

import com.danil.kleshchin.rss.domain.entity.Section
import org.junit.Assert.assertEquals
import org.junit.Test

class SectionTest {

    @Test
    fun `test Section enum with quotes in name`() {
        val expected = "Sunday-Review"
        val actual = Section.`T-Magazine`.name
        assertEquals(expected, actual)
    }
}
