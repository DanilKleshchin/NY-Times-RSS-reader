package com.danil.kleshchin.rss.data.feeds

import com.danil.kleshchin.rss.data.BuildConfig
import com.danil.kleshchin.rss.data.feeds.datasource.FeedDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiToDomainMapper
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import kotlinx.coroutines.withContext

class FeedDataRepository(
    private val remoteDataSource: FeedDataSource,
    private val mapper: FeedApiToDomainMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {

    override suspend fun getFeedListBySection(sectionName: String): List<Feed> =
        withContext(dispatcherProvider.network) {
            val apiKey = BuildConfig.API_KEY
            mapper.transform(
                remoteDataSource.getTopStoriesBySection(
                    sectionName.toLowerCase(),
                    apiKey
                )
            )
        }
}
