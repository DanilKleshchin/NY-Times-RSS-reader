package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.data.feeds.FeedDataRepository
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApi
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiToDomainMapper
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.network.baseUrl
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import com.danil.kleshchin.rss.screens.feedslist.FeedsListContract
import com.danil.kleshchin.rss.screens.feedslist.FeedsListNavigator
import com.danil.kleshchin.rss.screens.feedslist.FeedsListPresenter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class FeedsListModule(private val feedsListNavigator: FeedsListNavigator) {

    @Provides
    fun provideFeedPresenter(
        getFeedBySectionUseCase: GetFeedBySectionUseCase
    ): FeedsListContract.Presenter = FeedsListPresenter(getFeedBySectionUseCase, feedsListNavigator)

    @Provides
    fun provideFeedRepository(
        feedRemoteDataSource: FeedRemoteDataSource,
        mapper: FeedApiToDomainMapper,
        dispatcher: DispatcherProvider
    ): FeedRepository =
        FeedDataRepository(
            feedRemoteDataSource,
            mapper,
            dispatcher
        )

    @Provides
    fun provideFeedApi(okHttpClient: OkHttpClient): FeedApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FeedApi::class.java)
}
