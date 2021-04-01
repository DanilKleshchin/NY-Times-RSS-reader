package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity
import javax.inject.Inject

class FavoriteFeedLocalDataSource @Inject constructor(
    private val database: FavoriteFeedDatabase
) {

    suspend fun addFeed(feed: FavoriteFeedEntity) =
        database.feedDao.addFeed(feed)

    suspend fun removeFeeds() =
        database.feedDao.removeFeeds()

    suspend fun getFeedList(): List<FavoriteFeedEntity> =
        database.feedDao.getFeedList()

    suspend fun markFeedToRemove(id: Int, toRemove: Boolean) =
        database.feedDao.markFeedToRemove(id, toRemove)
}
