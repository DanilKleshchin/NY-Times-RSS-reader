package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.GetFavoriteFeedsListUseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
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

    fun loadFeedsList(): LiveData<ResultWrapper<List<Feed>>> {
        return liveData {
            val params = GetFeedListBySectionUseCase.Params(section.toSection().name)
            val feedList = getFeedBySectionUseCase.execute(params)
            emit(
                feedList
            )
        }
    }

    fun getFeedListWithFavorites(feedList: List<Feed>): LiveData<List<FeedEntity>> {
        return liveData {
            val favoritesList = getFavoriteFeedsListUseCase.execute(Unit)
            setFavoritesFeeds(feedList, favoritesList)
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
