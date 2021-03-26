package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import retrofit2.Response

interface FeedRemoteDataSource {

    suspend fun getFeedListBySection(sectionName: String): Response<FeedObjectApiEntity>
}
