package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

interface FavoriteFeedLocalDataSource {

    suspend fun addFeed(feed: FavoriteFeedEntity)

    suspend fun removeFeeds()

    suspend fun getFeedList(): List<FavoriteFeedEntity>

    suspend fun markFeedToRemove(id: Int, toRemove: Boolean)
}
