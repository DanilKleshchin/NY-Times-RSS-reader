package com.danil.kleshchin.rss.data.feeds.features.feedslist

import com.danil.kleshchin.rss.data.BuildConfig
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import java.util.Locale

class FeedDataRepository(
    private val remoteDataSource: FeedRemoteDataSource,
    private val localDataSource: FeedLocalDataSource,
    private val apiMapper: FeedApiMapper,
    private val dbMapper: FeedDbMapper,
    private val dispatcherProvider: DispatcherProvider
) : FeedRepository {


    override suspend fun getFeedListBySection(sectionName: String) =
        withContext(dispatcherProvider.network) {
            val remote =
                async(dispatcherProvider.network) { getRemoteFeedListBySection(sectionName) }
            //TODO ask how to implement Observable.concatArrayEager with coroutines.
            /*val local =
                async(dispatcherProvider.database) { getLocalApiFeedListBySection(sectionName) }*/
            select<List<Feed>> {
                remote.onAwait { it }
                /*local.onAwait { it }*/
            }.also { feeds ->
                updateLocalFeedList(sectionName, feeds)
            }
        }


    private suspend fun getRemoteFeedListBySection(sectionName: String) =
        apiMapper.transform(
            remoteDataSource.getFeedListBySection(
                sectionName.toLowerCase(Locale.getDefault()),
                BuildConfig.API_KEY
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
