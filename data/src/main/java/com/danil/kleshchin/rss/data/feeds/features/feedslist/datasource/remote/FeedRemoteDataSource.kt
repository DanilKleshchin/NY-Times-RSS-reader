package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity

interface FeedRemoteDataSource {

    suspend fun getFeedListBySection(sectionName: String): FeedObjectApiEntity
}
