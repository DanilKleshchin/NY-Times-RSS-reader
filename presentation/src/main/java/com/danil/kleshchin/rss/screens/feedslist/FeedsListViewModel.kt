package com.danil.kleshchin.rss.screens.feedslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.GetFavoriteFeedsListUseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetFeedListBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.section.SectionEntity
import com.danil.kleshchin.rss.entities.section.SectionMapper
import com.danil.kleshchin.rss.screens.BaseFeedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedsListViewModel : BaseFeedViewModel() {

    @Inject
    lateinit var getFavoriteFeedsListUseCase: GetFavoriteFeedsListUseCase

    @Inject
    lateinit var getFeedBySectionUseCase: GetFeedListBySectionUseCase

    @Inject
    lateinit var sectionMapper: SectionMapper

    @Inject
    lateinit var dispatcher: DispatcherProvider

    lateinit var section: SectionEntity

    private var feedEntityList: List<FeedEntity> = ArrayList()

    private val _feeds = MutableLiveData<ResultWrapper<List<FeedEntity>>>()
    val feeds: LiveData<ResultWrapper<List<FeedEntity>>>
        get() = _feeds

    fun loadFeedsList() {
        if (feedEntityList.isNotEmpty()) {
            _feeds.value = (ResultWrapper.Success(feedEntityList))
            return
        }
        loadFeeds()
    }

    fun loadUpdatedFeedsList() {
        loadFeeds()
    }

    private fun loadFeeds() {
        viewModelScope.launch(dispatcher.network) {
            val params = GetFeedListBySectionUseCase.Params(sectionMapper.transform(section).name)
            getFeedBySectionUseCase.execute(params)
                .collect { feeds ->
                    _feeds.postValue(
                        when (feeds) {
                            is ResultWrapper.Success -> ResultWrapper.Success(
                                getFeedListWithFavorites( // Set the isFavorite value to the same feed which was added to favorites
                                    feeds.value
                                )
                            )
                            is ResultWrapper.Error -> ResultWrapper.Error(feeds.exception)
                            else -> ResultWrapper.NetworkError
                        }
                    )
                }
        }
    }

    private suspend fun getFeedListWithFavorites(feedList: List<Feed>): List<FeedEntity> {
        val favoritesList = getFavoriteFeedsListUseCase.execute(Unit)
        setFavoritesFeeds(feedList, favoritesList)
        val currentTime = System.currentTimeMillis()
        feedEntityList =
            feedList.map { mapper.transform(it, currentTime, resourceHelper.getAndroidResources()) }
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
