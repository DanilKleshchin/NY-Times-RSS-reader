package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import retrofit2.Response
import javax.inject.Inject

class FeedRemoteDataSource @Inject constructor(
    private val feedApi: FeedApi
) {

    suspend fun getFeedListBySection(
        sectionName: String
    ): Response<FeedObjectApiEntity> {
        return feedApi.getTopStories(sectionName)
    }
}
