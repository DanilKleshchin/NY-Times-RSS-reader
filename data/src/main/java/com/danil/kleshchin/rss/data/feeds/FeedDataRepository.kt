package com.danil.kleshchin.rss.data.feeds

import com.danil.kleshchin.rss.data.BuildConfig
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import kotlinx.coroutines.withContext

class FeedDataRepository(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val apiMapper: FeedApiMapper,
    private val dbMapper: FeedDbMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {

    override suspend fun getFeedListBySection(sectionName: String): List<Feed> =
        withContext(dispatcherProvider.network) {
            val apiKey = BuildConfig.API_KEY
            val feedList = apiMapper.transform(
                remoteDataSource.getFeedListBySection(
                    sectionName.toLowerCase(),
                    apiKey
                )
            )
            localDataSource.setFeedList(dbMapper.transformToDb(feedList))
            feedList
        }
}
