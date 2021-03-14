package com.danil.kleshchin.rss.screens.feed

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

class FeedViewModel : ViewModel() {

    var addRemoveFavoritesJob: Job? = null

    @Inject
    lateinit var resourceHelper: ResourceHelper

    @Inject
    lateinit var addFeedToFavouritesUseCase: AddFeedToFavouritesUseCase

    @Inject
    lateinit var removeFeedFromFavoritesUseCase: RemoveFeedFromFavoritesUseCase

    @Inject
    lateinit var mapper: FeedMapper

    lateinit var feed: FeedEntity //TODO checkout data passing between viewModels

    val title
        get() = feed.title
    val kicker
        get() = feed.kicker
    val author
        get() = feed.author
    val iconUrl
        get() = feed.iconUrl
    val iconCaption
        get() = feed.iconCaption
    val iconCopyright
        get() = feed.iconCopyright
    val description
        get() = feed.description
    val dateCreated
        get() = resourceHelper.getDateCreated(feed.dateCreated)
    val dateUpdated
        get() = resourceHelper.getDateUpdated(feed.dateUpdated)
    val isFavorite
        get() = feed.isFavorite

    fun onStarClick() {
        addRemoveFavoritesJob?.cancel()

        addRemoveFavoritesJob = viewModelScope.launch {
            if (feed.isFavorite.get()) {
                removeFeedFromFavoritesUseCase.execute(mapper.transform(feed))
            } else {
                addFeedToFavouritesUseCase.execute(mapper.transform(feed))
            }
            feed.isFavorite.set(feed.isFavorite.get().not()) // changes the star icon after adding/removing the feed to/from favorites
        }
    }
}
