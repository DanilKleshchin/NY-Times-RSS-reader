package com.danil.kleshchin.rss.screens.feed

import androidx.databinding.ObservableBoolean
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
    val isFavorite: ObservableBoolean = ObservableBoolean()
        get() {
            field.set(feed.isFavorite) //TODO try to refactor this
            return field
        }

    fun onStarIconClick() {
        addRemoveFavoritesJob?.cancel()

        addRemoveFavoritesJob = viewModelScope.launch {
            feed.isFavorite = feed.isFavorite.not()
            if (feed.isFavorite) {
                addFeedToFavouritesUseCase.execute(mapper.transform(feed))
            } else {
                removeFeedFromFavoritesUseCase.execute(mapper.transform(feed))
            }
            isFavorite.set(feed.isFavorite) // changes the star icon after adding/removing the feed to/from favorites
        }
    }
}
