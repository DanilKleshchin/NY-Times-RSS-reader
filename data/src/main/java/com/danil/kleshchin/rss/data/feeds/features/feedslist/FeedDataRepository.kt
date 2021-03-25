package com.danil.kleshchin.rss.data.feeds.features.feedslist

import android.content.Context
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.data.feeds.utils.isNetworkAvailable
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import java.util.Locale

class FeedDataRepository(
    private val context: Context,
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
    override suspend fun getFeedListBySection(sectionName: String): Flow<ResultWrapper<List<Feed>>> =
        channelFlow {
            try {
                if (isNetworkAvailable(context)) {
                    withContext(dispatcherProvider.network) {
                        val result = getRemoteFeedListBySection(sectionName)
                        send(result)
                        if (result is ResultWrapper.Success) {
                            updateLocalFeedList(sectionName, result.value)
                        } else {
                            send(getLocalFeedListBySection(sectionName))
                        }
                    }
                } else {
                    send(ResultWrapper.NetworkError)
                    withContext(dispatcherProvider.database) {
                        send(getLocalFeedListBySection(sectionName))
                    }
                }
            } catch (exception: Exception) {
                send(ResultWrapper.Error(exception))
            }
        }

    /**
     * Feeds from [com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApi]
     */
    private suspend fun getRemoteFeedListBySection(sectionName: String): ResultWrapper<List<Feed>> {
        val feeds = remoteDataSource.getFeedListBySection(
            sectionName.toLowerCase(Locale.getDefault())
        )
            .results.map { apiMapper.transform(it) }
        return ResultWrapper.Success(feeds)
    }

    /**
     * Feeds from [com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDatabase]
     */
    private suspend fun getLocalFeedListBySection(sectionName: String): ResultWrapper<List<Feed>> {
        val feeds = localDataSource.getFeedListBySection(sectionName)
            .map { dbMapper.transformToDomain(it) }
        return ResultWrapper.Success(feeds)
    }

    private suspend fun updateLocalFeedList(sectionName: String, list: List<Feed>) {
        localDataSource.removeFeedBySection(sectionName)
        localDataSource.setFeedList(list.mapIndexed { index, feed ->
            dbMapper.transformToDb(sectionName, index, feed)
        })
    }
}
