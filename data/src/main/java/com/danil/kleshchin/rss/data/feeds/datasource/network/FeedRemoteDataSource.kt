package com.danil.kleshchin.rss.data.feeds.datasource.network

import com.danil.kleshchin.rss.data.feeds.datasource.FeedDataSource
import com.danil.kleshchin.rss.data.feeds.entity.FeedObjectApiEntity
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val feedApi: FeedApi
) : FeedDataSource {

    override suspend fun getTopStoriesBySection(
        section: String,
        apiKey: String
    ): FeedObjectApiEntity {
        return feedApi.getTopStories(section, apiKey)
    }
}
