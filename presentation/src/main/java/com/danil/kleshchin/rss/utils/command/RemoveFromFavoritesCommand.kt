package com.danil.kleshchin.rss.utils.command

import com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases.MarkFeedToRemoveFromFavoritesUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity

class RemoveFromFavoritesCommand(
    private val feed: FeedEntity,
    private val markFeedToRemoveFromFavoritesUseCase: MarkFeedToRemoveFromFavoritesUseCase
) : FeedWithUndoCommand {

    override suspend fun execute() {
        removeFavoriteFeed(feed)
    }

    override suspend fun undo() {
        feed.isFavorite.set(true)
        markFeedToRemoveFromFavoritesUseCase.execute(
            MarkFeedToRemoveFromFavoritesUseCase.Params(
                feed.id,
                false
            )
        )
    }

    private suspend fun removeFavoriteFeed(feed: FeedEntity) {
        markFeedToRemoveFromFavoritesUseCase.execute(
            MarkFeedToRemoveFromFavoritesUseCase.Params(
                feed.id,
                true
            )
        )
    }
}
