package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.entities.section.SectionEntity
import javax.inject.Inject

class FeedsListViewModel : ViewModel() {

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedBySectionUseCase

    @Inject
    lateinit var feedMapper: FeedMapper

    lateinit var section: SectionEntity //TODO checkout data passing between viewModels

    fun loadFeedsList(): LiveData<List<Feed>> {
        return liveData {
            val params = GetFeedBySectionUseCase.Params(section.toSection().name)
            emit(
                getFeedBySectionUseCase.execute(params)
            )
        }
    }
}
