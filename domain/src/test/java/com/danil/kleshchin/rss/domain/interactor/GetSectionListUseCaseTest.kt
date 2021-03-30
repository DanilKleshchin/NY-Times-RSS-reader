package com.danil.kleshchin.rss.domain.interactor

import com.danil.kleshchin.rss.domain.BaseTestCoroutineScope
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetSectionListUseCase
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetSectionListUseCaseTest : BaseTestCoroutineScope() {

    private val T_MAGAZINE_SECTION = Section.`T-Magazine`

    private lateinit var getSectionListUseCase: GetSectionListUseCase
    private lateinit var sectionList: List<Section>

    @Before
    fun setUp() {
        getSectionListUseCase = GetSectionListUseCase()
        testCoroutineScope.launch {
            sectionList = getSectionListUseCase.execute(Unit)
        }
    }

    @Test
    fun `test section list contains t-magazine section`() {
        Assert.assertTrue(sectionList.contains(T_MAGAZINE_SECTION))
    }
}
