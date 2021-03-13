package com.danil.kleshchin.rss.data.feeds.datasource.network

import com.danil.kleshchin.rss.data.feeds.datasource.network.entity.FeedObjectApiEntity

interface FeedRemoteDataSource {

    suspend fun getFeedListBySection(sectionName: String, apiKey: String): FeedObjectApiEntity
}
