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
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FeedsListViewModel : BaseFeedViewModel() {

    @Inject
    lateinit var getFavoriteFeedsListUseCase: GetFavoriteFeedsListUseCase

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedListBySectionUseCase

    lateinit var section: SectionEntity

    private var feedEntityList: List<FeedEntity> = ArrayList()

    //TODO ask how to do that
    fun loadFeedsList(): LiveData<ResultWrapper<List<FeedEntity>>> {
        if (feedEntityList.isNotEmpty()) {
            return liveData {
                emit(ResultWrapper.Success(feedEntityList))
            }
        } else {
            return loadUpdatedFeedsList()
        }
    }

    fun loadUpdatedFeedsList(): LiveData<ResultWrapper<List<FeedEntity>>> {
        return liveData {
            val params = GetFeedListBySectionUseCase.Params(section.toSection().name)
            getFeedBySectionUseCase.execute(params)
                .collect { feeds ->
                    when (feeds) {
                        is ResultWrapper.Success -> emit(
                            ResultWrapper.Success(
                                getFeedListWithFavorites(feeds.value)
                            )
                        )
                        is ResultWrapper.Error -> emit(feeds)
                        is ResultWrapper.NetworkError -> emit(feeds)
                    }
                }
        }
    }

    private suspend fun getFeedListWithFavorites(feedList: List<Feed>): List<FeedEntity> {
        val favoritesList = getFavoriteFeedsListUseCase.execute(Unit)
        setFavoritesFeeds(feedList, favoritesList)
        val currentTime = System.currentTimeMillis()
        feedEntityList = mapper.transform(feedList, currentTime, resourceHelper.getAndroidResources())
        return feedEntityList
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
