package com.danil.kleshchin.rss.domain.interactor

import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetFeedBySectionUseCaseTest {

    private val SECTION_ID = 5

    private lateinit var getFeedBySection: GetFeedListBySectionUseCase

    @Mock
    private lateinit var mockFeedRepository: FeedRepository

    @Before
    fun setUp() {
        getFeedBySection = GetFeedListBySectionUseCase(mockFeedRepository)
    }

    @Test
    fun testGetFeedBySectionUseCase() {
        getFeedBySection.execute(GetFeedListBySectionUseCase.Params(SECTION_ID))

        verify(mockFeedRepository).getFeedListBySection(SECTION_ID)
        verifyNoMoreInteractions(mockFeedRepository)
    }
}
