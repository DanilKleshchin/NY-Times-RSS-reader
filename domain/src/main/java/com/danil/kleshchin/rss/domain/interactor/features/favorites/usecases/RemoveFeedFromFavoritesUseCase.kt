package com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import javax.inject.Inject

class RemoveFeedFromFavoritesUseCase @Inject constructor(
    private val repository: FavoriteFeedRepository
) : UseCase<Unit, Feed> {

    //TODO think about param
    override suspend fun execute(params: Feed) {
        repository.removeFeedFromFavorites(params)
    }
}
