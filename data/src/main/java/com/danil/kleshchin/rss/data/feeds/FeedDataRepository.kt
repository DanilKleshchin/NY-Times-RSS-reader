package com.danil.kleshchin.rss.data.feeds

import com.danil.kleshchin.rss.data.BuildConfig
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiToDomainMapper
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import kotlinx.coroutines.withContext

class FeedDataRepository(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val mapper: FeedApiToDomainMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {

    override suspend fun getFeedListBySection(sectionName: String): List<Feed> =
        withContext(dispatcherProvider.network) {
            val apiKey = BuildConfig.API_KEY
            mapper.transform(
                remoteDataSource.getFeedListBySection(
                    sectionName.toLowerCase(),
                    apiKey
                )
            )
        }
}
