package com.danil.kleshchin.rss.domain.interactor

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetSectionListUseCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSectionListUseCaseTest {

    private val SUNDAY_SECTION = Section.`Sunday-Review`

    private lateinit var getSectionListUseCase: GetSectionListUseCase
    private lateinit var sectionList: List<Section>

    @Before
    fun setUp() {
        getSectionListUseCase = GetSectionListUseCase()
        sectionList = getSectionListUseCase.getSectionList()
    }

    @Test
    fun `test section list with real section`() {
        Assert.assertTrue(sectionList.contains(SUNDAY_SECTION))
    }
}
