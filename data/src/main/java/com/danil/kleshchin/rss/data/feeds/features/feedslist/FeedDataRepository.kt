package com.danil.kleshchin.rss.data.feeds.features.feedslist

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Locale

class FeedDataRepository(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val apiMapper: FeedApiMapper,
    private val dbMapper: FeedDbMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {

    /**
     * Returns feeds from the remote data source and updates it in the local data source.
     */
    @ExperimentalCoroutinesApi
    override suspend fun getFeedListBySection(sectionName: String): Flow<Result<List<Feed>>> =
        channelFlow {
            try {
                withContext(dispatcherProvider.network) {
                    val result = getRemoteFeedListBySection(sectionName)
                    send(result)
                    if (result is Result.Success) {
                        updateLocalFeedList(sectionName, result.value)
                    } else {
                        send(getLocalFeedListBySection(sectionName))
                    }
                }
            } catch (exception: Exception) {
                send(Result.Error(exception))
                withContext(dispatcherProvider.database) {
                    send(getLocalFeedListBySection(sectionName))
                }
            }
        }

    /**
     * Feeds from [com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApi]
     */
    private suspend fun getRemoteFeedListBySection(sectionName: String): Result<List<Feed>> {
        val response = remoteDataSource.getFeedListBySection(
            sectionName.toLowerCase(Locale.getDefault())
        )
        if (response.isSuccessful && response.body() != null) {
            return Result.Success(response.body()!!.results.map { apiMapper.transform(it) })
        }
        return Result.Error(HttpException(response))
    }

    /**
     * Feeds from [com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDatabase]
     */
    private suspend fun getLocalFeedListBySection(sectionName: String): Result<List<Feed>> {
        val feeds = localDataSource.getFeedListBySection(sectionName)
            .map { dbMapper.transformToDomain(it) }
        return Result.Success(feeds)
    }

    private suspend fun updateLocalFeedList(sectionName: String, list: List<Feed>) {
        localDataSource.removeFeedBySection(sectionName)
        localDataSource.setFeedList(list.mapIndexed { index, feed ->
            dbMapper.transformToDb(sectionName, index, feed)
        })
    }
}
