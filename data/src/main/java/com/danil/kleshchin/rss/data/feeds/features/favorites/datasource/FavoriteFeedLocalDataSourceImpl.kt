package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

class FavoriteFeedLocalDataSourceImpl(
    private val database: FavoriteFeedDatabase
) : FavoriteFeedLocalDataSource {

    override suspend fun addFeedToFavorites(feed: FavoriteFeedEntity) {
        database.feedDao.addFeedToFavorites(feed)
    }

    override suspend fun removeFeedFromFavorites(feed: FavoriteFeedEntity) {
        database.feedDao.removeFeedFromFavorites(feed.id)
    }

    override suspend fun getFavoritesFeedList(): List<FavoriteFeedEntity> {
        return database.feedDao.getFavoritesFeedList()
    }
}
