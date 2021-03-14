package com.danil.kleshchin.rss.domain.interactor.features.favorites.usecases

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import javax.inject.Inject

class GetFavoritesFeedListUseCase @Inject constructor(
    private val repository: FavoriteFeedRepository
) : UseCase<List<Feed>, Unit> {

    override suspend fun execute(params: Unit): List<Feed> {
        return repository.getFavoritesFeedList()
    }
}
