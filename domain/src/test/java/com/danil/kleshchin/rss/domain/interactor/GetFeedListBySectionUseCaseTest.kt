package com.danil.kleshchin.rss.domain.interactor

import com.danil.kleshchin.rss.domain.BaseTestCoroutineScope
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

const val SECTION_NAME = "Arts"

@RunWith(MockitoJUnitRunner::class)
class GetFeedListBySectionUseCaseTest : BaseTestCoroutineScope() {

    private lateinit var getFeedBySection: GetFeedListBySectionUseCase

    @Mock
    private lateinit var mockFeedRepository: FeedRepository

    @Before
    fun setUp() {
        getFeedBySection = GetFeedListBySectionUseCase(mockFeedRepository)
    }

    @Test
    fun testGetFeedListBySectionUseCase() {
        testCoroutineScope.launch {
            getFeedBySection.execute(GetFeedListBySectionUseCase.Params(SECTION_NAME))
            verify(mockFeedRepository).getFeedListBySection(SECTION_NAME)
            verifyNoMoreInteractions(mockFeedRepository)
        }
    }
}
