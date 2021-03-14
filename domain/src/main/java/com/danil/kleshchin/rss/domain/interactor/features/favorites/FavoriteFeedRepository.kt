package com.danil.kleshchin.rss.domain.interactor.features.favorites

import com.danil.kleshchin.rss.domain.entity.Feed


interface FavoriteFeedRepository {

    suspend fun addFeedToFavorites(feed: Feed)

    suspend fun removeFeedFromFavorites(feed: Feed)

    suspend fun getFavoritesFeedList(): List<Feed>
}
