package com.danil.kleshchin.rss.data.feeds.features.favorites

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedMapper
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository

class FavoriteFeedDataRepository(
    private val localDataSource: FavoriteFeedLocalDataSource,
    private val mapper: FavoriteFeedMapper
) : FavoriteFeedRepository {

    override suspend fun addFeedToFavorites(feed: Feed) =
        localDataSource.addFeedToFavorites(mapper.transformToDb(feed))

    override suspend fun removeFeedFromFavorites(feed: Feed) =
        localDataSource.removeFeedFromFavorites(mapper.transformToDb(feed))

    override suspend fun getFavoritesFeedList(): List<Feed> =
        mapper.transformToDomain(localDataSource.getFavoritesFeedList())
}
