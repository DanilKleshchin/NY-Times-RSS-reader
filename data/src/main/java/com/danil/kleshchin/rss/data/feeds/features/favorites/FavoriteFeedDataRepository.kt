package com.danil.kleshchin.rss.data.feeds.features.favorites

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedMapper
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository

class FavoriteFeedDataRepository(
    private val localDataSource: FavoriteFeedLocalDataSource,
    private val mapper: FavoriteFeedMapper
) : FavoriteFeedRepository {

    override suspend fun addFeed(feed: Feed) =
        localDataSource.addFeed(mapper.transformToDb(feed))

    override suspend fun removeFeeds() =
        localDataSource.removeFeeds()

    override suspend fun getFeedList(): List<Feed> =
        localDataSource.getFeedList().map { mapper.transformToDomain(it) }

    override suspend fun markFeedToRemove(id: Int, toRemove: Boolean) =
        localDataSource.markFeedToRemove(id, toRemove)
}
