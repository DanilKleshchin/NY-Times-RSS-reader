package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.GetFavoriteFeedsListUseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.screens.BaseFeedViewModel
import javax.inject.Inject

class FeedsListViewModel : BaseFeedViewModel() {

    @Inject
    lateinit var getFavoriteFeedsListUseCase: GetFavoriteFeedsListUseCase

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedListBySectionUseCase

    lateinit var section: SectionEntity

    fun loadFeedsList(): LiveData<List<FeedEntity>> {
        return liveData {
            val params = GetFeedListBySectionUseCase.Params(section.toSection().name)
            val feedList = getFeedBySectionUseCase.execute(params) //feeds from API or DB
            val favoritesList = getFavoriteFeedsListUseCase.execute(Unit) //feeds from favorites DB
            setFavoritesFeeds(feedList, favoritesList) //set isFavorite field to feeds from API or DB
            val currentTime = System.currentTimeMillis()
            val feedEntityList =
                mapper.transform(feedList, currentTime, resourceHelper.getAndroidResources())
            emit(
                feedEntityList
            )
        }
    }

    /**
     * Set favorites feeds to the loaded feeds list
     */
    private fun setFavoritesFeeds(feedList: List<Feed>, favoritesFeeds: List<Feed>) {
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
