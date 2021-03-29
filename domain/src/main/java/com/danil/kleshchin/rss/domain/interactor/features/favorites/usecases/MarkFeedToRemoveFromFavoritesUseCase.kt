package com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases

import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import javax.inject.Inject

/**
 * Set the toRemove field true or false. That feed should be deleted later
 */
class MarkFeedToRemoveFromFavoritesUseCase @Inject constructor(
    private val repository: FavoriteFeedRepository
) : UseCase<Unit, MarkFeedToRemoveFromFavoritesUseCase.Params> {

    override suspend fun execute(params: Params) {
        repository.markFeedToRemove(params.id, params.toRemove)
    }

    data class Params(
        val id: Int,
        val toRemove: Boolean
    )
}
