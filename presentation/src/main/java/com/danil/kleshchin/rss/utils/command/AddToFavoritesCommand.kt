package com.danil.kleshchin.rss.utils.command

import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.AddFeedToFavouritesUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper

class AddToFavoritesCommand(
    private val feed: FeedEntity,
    private val addFeedToFavouritesUseCase: AddFeedToFavouritesUseCase,
    private var mapper: FeedMapper
) : FeedCommand {

    override suspend fun execute() {
        addFavoriteFeed(feed)
    }

    private suspend fun addFavoriteFeed(feed: FeedEntity) {
        addFeedToFavouritesUseCase.execute(mapper.transform(feed))
    }
}
