package com.danil.kleshchin.rss.data.feeds.features.feedslist

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
import kotlinx.coroutines.withContext
import java.util.Locale

class FeedDataRepository(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val apiMapper: FeedApiMapper,
    private val dbMapper: FeedDbMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {


    override suspend fun getFeedListBySection(sectionName: String): ResultWrapper<List<Feed>> =
        try {
            withContext(dispatcherProvider.network) {
                getRemoteFeedListBySection(sectionName)
            }
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }


    private suspend fun getRemoteFeedListBySection(sectionName: String) =
        ResultWrapper.Success(
            apiMapper.transform(
                remoteDataSource.getFeedListBySection(
                    sectionName.toLowerCase(Locale.getDefault())
                )
            )
        )


    private suspend fun getLocalApiFeedListBySection(sectionName: String) =
        dbMapper.transformToDomain(
            localDataSource.getFeedListBySection(sectionName.toLowerCase(Locale.getDefault()))
        )

    private suspend fun updateLocalFeedList(sectionName: String, list: List<Feed>) {
        localDataSource.removeFeedBySection(sectionName)
        localDataSource.setFeedList(dbMapper.transformToDb(sectionName, list))
    }
}
