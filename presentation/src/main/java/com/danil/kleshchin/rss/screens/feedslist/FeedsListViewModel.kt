package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.entities.section.SectionEntity
import javax.inject.Inject

class FeedsListViewModel : ViewModel() {

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedListBySectionUseCase

    @Inject
    lateinit var feedMapper: FeedMapper

    lateinit var section: SectionEntity //TODO checkout data passing between viewModels

    fun loadFeedsList(): LiveData<List<Feed>> {
        return liveData {
            val params = GetFeedListBySectionUseCase.Params(section.toSection().name)
            emit(
                getFeedBySectionUseCase.execute(params)
            )
        }
    }

    fun getFavoritesFeedList(): List<Feed> = arrayListOf()

    /**
     * Set favorites feeds to the loaded feeds list
     */
    fun setFavoritesFeeds(feedList: List<Feed>, favoritesFeeds: List<Feed>) {
        feedList.forEach { feed ->
            loop@ for (favorite in favoritesFeeds) {
                if (feed == favorite) {
                    feed.isFavorite = true
                    break@loop
                }
            }
        }
    }
}
