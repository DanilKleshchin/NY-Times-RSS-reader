package com.danil.kleshchin.rss.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.AddFeedToFavouritesUseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.RemoveFeedFromFavoritesUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.utils.ResourceHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This viewModel created for managing favorites viewModels
 */
abstract class BaseFeedViewModel : ViewModel() {

    var addRemoveFavoritesJob: Job? = null

    @Inject
    lateinit var resourceHelper: ResourceHelper

    @Inject
    lateinit var addFeedToFavouritesUseCase: AddFeedToFavouritesUseCase

    @Inject
    lateinit var removeFeedFromFavoritesUseCase: RemoveFeedFromFavoritesUseCase

    @Inject
    lateinit var mapper: FeedMapper

    fun addRemoveFavoriteFeed(feed: FeedEntity) {
        addRemoveFavoritesJob?.cancel()

        addRemoveFavoritesJob = viewModelScope.launch {
            feed.isFavorite.set(feed.isFavorite.get().not())
            if (feed.isFavorite.get()) {
                addFeedToFavouritesUseCase.execute(mapper.transform(feed))
            } else {
                removeFeedFromFavoritesUseCase.execute(mapper.transform(feed))
            }
        }
    }
}
