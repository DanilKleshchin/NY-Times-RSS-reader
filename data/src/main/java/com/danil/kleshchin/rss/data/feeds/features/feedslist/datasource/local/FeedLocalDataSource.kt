package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity

interface FeedLocalDataSource {

    suspend fun setFeedList(feedList: List<FeedDbEntity>)

    suspend fun removeFeedBySection(sectionName: String)

    suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity>

    suspend fun removeAll()
}
