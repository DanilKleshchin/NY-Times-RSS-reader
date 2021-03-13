package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.data.feeds.FeedDataRepository
import com.danil.kleshchin.rss.data.feeds.datasource.network.BASE_URL
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApi
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiToDomainMapper
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class FeedsListModule {

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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FeedApi::class.java)
}
