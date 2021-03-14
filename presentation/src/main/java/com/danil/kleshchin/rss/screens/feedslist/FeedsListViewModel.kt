package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.AddFeedToFavouritesUseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.GetFavoritesFeedListUseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.RemoveFeedFromFavoritesUseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.utils.ResourceHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedsListViewModel : ViewModel() {

    var addRemoveFavoritesJob: Job? = null //TODO ask about the same behavior in different viewModels/fragments (adding to favorites)

    @Inject
    lateinit var addFeedToFavouritesUseCase: AddFeedToFavouritesUseCase

    @Inject
    lateinit var removeFeedFromFavoritesUseCase: RemoveFeedFromFavoritesUseCase

    @Inject
    lateinit var getFavoritesFeedListUseCase: GetFavoritesFeedListUseCase

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedListBySectionUseCase

    @Inject
    lateinit var feedMapper: FeedMapper

    @Inject
    lateinit var resourceHelper: ResourceHelper

    lateinit var section: SectionEntity //TODO checkout data passing between viewModels

    fun loadFeedsList(): LiveData<List<FeedEntity>> {
        return liveData {
            val params = GetFeedListBySectionUseCase.Params(section.toSection().name)
            val feedList = getFeedBySectionUseCase.execute(params) //feeds from API or DB
            val favoritesList = getFavoritesFeedListUseCase.execute(Unit) //feeds from favorites DB
            setFavoritesFeeds(feedList, favoritesList) //set isFavorite field to feeds from API or DB
            val currentTime = System.currentTimeMillis()
            val feedEntityList =
                feedMapper.transform(feedList, currentTime, resourceHelper.getAndroidResources())
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

    //TODO ask about repeating code
    fun onStarClick(feed: FeedEntity) {
        addRemoveFavoritesJob?.cancel()

        addRemoveFavoritesJob = viewModelScope.launch {
            if (feed.isFavorite.get()) {
                removeFeedFromFavoritesUseCase.execute(feedMapper.transform(feed))
            } else {
                addFeedToFavouritesUseCase.execute(feedMapper.transform(feed))
            }
            feed.isFavorite.set(feed.isFavorite.get().not()) // changes the star icon after adding/removing the feed to/from favorites
        }
    }
}
