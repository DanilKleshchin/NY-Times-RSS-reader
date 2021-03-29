package com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases

import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import javax.inject.Inject

/**
 * Remove all feeds that were marked to delete
 */
class RemoveFeedFromFavoritesUseCase @Inject constructor(
    private val repository: FavoriteFeedRepository
) : UseCase<Unit, Unit> {

    override suspend fun execute(params: Unit) {
        repository.removeFeeds()
    }
}
