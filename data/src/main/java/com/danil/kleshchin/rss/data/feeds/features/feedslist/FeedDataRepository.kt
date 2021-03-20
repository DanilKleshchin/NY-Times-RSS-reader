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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

class FeedDataRepository(
    private val context: Context,
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val apiMapper: FeedApiMapper,
    private val dbMapper: FeedDbMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {

    override suspend fun getFeedListBySection(sectionName: String): Flow<ResultWrapper<List<Feed>>> =
        channelFlow {
            try {
                coroutineScope {
                    getLocalFeedListBySection(sectionName)
                        .flowOn(dispatcherProvider.database)
                        .onEach {
                            if (it.value.isNotEmpty()) {
                                send(it)
                            }
                        }
                        .launchIn(this)

                    if (isNetworkAvailable(context)) {
                        getRemoteFeedListBySection(sectionName)
                            .flowOn(dispatcherProvider.network)
                            .onEach { resultWrapper ->
                                updateLocalFeedList(sectionName, resultWrapper.value)
                                send(resultWrapper)
                            }
                            .launchIn(this)
                    } else {
                        send(ResultWrapper.NetworkError)
                    }
                }
            } catch (exception: Exception) {
                send(ResultWrapper.Error(exception))
            }
        }

    private suspend fun getRemoteFeedListBySection(sectionName: String) =
        flow {
            val feeds = remoteDataSource.getFeedListBySection(
                sectionName.toLowerCase(Locale.getDefault())
            ).results.map { apiMapper.transform(it) }
            emit(
                ResultWrapper.Success(feeds)
            )
        }

    private suspend fun getLocalFeedListBySection(sectionName: String) =
        flow {
            val feeds = localDataSource.getFeedListBySection(sectionName)
                .map { dbMapper.transformToDomain(it) }
            emit(
                ResultWrapper.Success(feeds)
            )
        }

    private suspend fun updateLocalFeedList(sectionName: String, list: List<Feed>) {
        localDataSource.removeFeedBySection(sectionName)
        localDataSource.setFeedList(list.map { dbMapper.transformToDb(sectionName, it) })
    }
}
