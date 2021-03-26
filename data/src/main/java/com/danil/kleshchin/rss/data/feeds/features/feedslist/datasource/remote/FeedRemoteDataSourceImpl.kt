package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import retrofit2.Response

class FeedRemoteDataSourceImpl(
    private val feedApi: FeedApi
) : FeedRemoteDataSource {

    override suspend fun getFeedListBySection(
        sectionName: String
    ): Response<FeedObjectApiEntity> {
        return feedApi.getTopStories(sectionName)
    }
}
