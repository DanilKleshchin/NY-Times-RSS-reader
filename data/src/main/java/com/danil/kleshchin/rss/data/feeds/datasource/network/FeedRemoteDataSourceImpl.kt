package com.danil.kleshchin.rss.data.feeds.datasource.network

import com.danil.kleshchin.rss.data.feeds.datasource.network.entity.FeedObjectApiEntity

class FeedRemoteDataSourceImpl(
    private val feedApi: FeedApi
) : FeedRemoteDataSource {

    override suspend fun getFeedListBySection(
        sectionName: String,
        apiKey: String
    ): FeedObjectApiEntity {
        return feedApi.getTopStories(sectionName, apiKey)
    }
}
