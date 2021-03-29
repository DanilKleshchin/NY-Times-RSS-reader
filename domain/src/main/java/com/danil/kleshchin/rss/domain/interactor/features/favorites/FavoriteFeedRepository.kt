package com.danil.kleshchin.rss.domain.interactor.features.favorites

import com.danil.kleshchin.rss.domain.entity.Feed


interface FavoriteFeedRepository {

    suspend fun addFeed(feed: Feed)

    suspend fun removeFeeds()

    suspend fun getFeedList(): List<Feed>

    suspend fun markFeedToRemove(id: Int, toRemove: Boolean)
}
