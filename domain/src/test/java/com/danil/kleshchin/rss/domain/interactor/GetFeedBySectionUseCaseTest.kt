package com.danil.kleshchin.rss.domain.interactor

import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
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

    private lateinit var getFeedBySection: GetFeedBySectionUseCase

    @Mock
    private lateinit var mockFeedRepository: FeedRepository

    @Before
    fun setUp() {
        getFeedBySection = GetFeedBySectionUseCase(mockFeedRepository)
    }

    @Test
    fun testGetFeedBySectionUseCase() {
        getFeedBySection.execute(GetFeedBySectionUseCase.Params(SECTION_ID))

        verify(mockFeedRepository).getFeedListBySection(SECTION_ID)
        verifyNoMoreInteractions(mockFeedRepository)
    }
}
