package com.danil.kleshchin.rss.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.AddFeedToFavouritesUseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.MarkFeedToRemoveFromFavoritesUseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.RemoveFeedFromFavoritesUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.utils.ResourceHelper
import com.danil.kleshchin.rss.utils.command.AddToFavoritesCommand
import com.danil.kleshchin.rss.utils.command.FeedCommandHistory
import com.danil.kleshchin.rss.utils.command.RemoveFromFavoritesCommand
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This viewModel created for managing favorites viewModels
 */
abstract class BaseFeedViewModel : ViewModel() {

    @Inject
    lateinit var resourceHelper: ResourceHelper

    @Inject
    lateinit var addFeedToFavouritesUseCase: AddFeedToFavouritesUseCase

    @Inject
    lateinit var markFeedToRemoveFromFavoritesUseCase: MarkFeedToRemoveFromFavoritesUseCase

    @Inject
    lateinit var removeFeedFromFavoritesUseCase: RemoveFeedFromFavoritesUseCase

    @Inject
    lateinit var mapper: FeedMapper

    override fun onCleared() {
        super.onCleared()
        removeFeeds()
    }

    private fun removeFeeds() {
        GlobalScope.launch {
            removeFeedFromFavoritesUseCase.execute(Unit)
        }
    }

    fun addRemoveFavoriteFeed(feed: FeedEntity) {
        viewModelScope.launch {
            feed.isFavorite.set(feed.isFavorite.get().not())
            if (feed.isFavorite.get()) {
                AddToFavoritesCommand(feed, addFeedToFavouritesUseCase, mapper).execute()
            } else {
                val command = RemoveFromFavoritesCommand(feed, markFeedToRemoveFromFavoritesUseCase)
                command.execute()
                FeedCommandHistory.push(command) // Add the removing operation to history, because only it can be undone
            }
        }
    }

    fun undoFeedRemoving(feed: FeedEntity) {
        viewModelScope.launch {
            FeedCommandHistory.pop().undo()
            AddToFavoritesCommand(feed, addFeedToFavouritesUseCase, mapper).execute()
        }
    }
}
