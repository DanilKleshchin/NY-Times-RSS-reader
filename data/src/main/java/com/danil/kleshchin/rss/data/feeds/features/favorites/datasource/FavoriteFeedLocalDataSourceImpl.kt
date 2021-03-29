package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

class FavoriteFeedLocalDataSourceImpl(
    private val database: FavoriteFeedDatabase
) : FavoriteFeedLocalDataSource {

    override suspend fun addFeed(feed: FavoriteFeedEntity) =
        database.feedDao.addFeed(feed)

    override suspend fun removeFeeds() =
        database.feedDao.removeFeeds()

    override suspend fun getFeedList(): List<FavoriteFeedEntity> =
        database.feedDao.getFeedList()

    override suspend fun markFeedToRemove(id: Int, toRemove: Boolean) =
        database.feedDao.markFeedToRemove(id, toRemove)
}
