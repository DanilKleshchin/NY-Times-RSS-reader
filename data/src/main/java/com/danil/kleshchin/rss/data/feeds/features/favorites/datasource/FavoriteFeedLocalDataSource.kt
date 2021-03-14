package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

interface FavoriteFeedLocalDataSource {

    suspend fun addFeedToFavorites(feed: FavoriteFeedEntity)

    suspend fun removeFeedFromFavorites(feed: FavoriteFeedEntity)

    suspend fun getFavoritesFeedList(): List<FavoriteFeedEntity>
}
