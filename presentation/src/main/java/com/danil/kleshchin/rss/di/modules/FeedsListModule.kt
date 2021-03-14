package com.danil.kleshchin.rss.di.modules

import android.content.Context
import com.danil.kleshchin.rss.data.feeds.features.feedslist.FeedDataRepository
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDatabase
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.BASE_URL
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApi
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [AppModule::class])
class FeedsListModule {

    @Provides
    fun provideRemoteDataSource(feedApi: FeedApi): FeedRemoteDataSource =
        FeedRemoteDataSourceImpl(feedApi)

    @Provides
    fun provideLocalDataSource(feedDatabase: FeedDatabase): FeedLocalDataSource =
        FeedLocalDataSourceImpl(feedDatabase)

    @Provides
    fun provideFeedRepository(
        feedRemoteDataSource: FeedRemoteDataSource,
        feedLocalDataSource: FeedLocalDataSource,
        apiMapper: FeedApiMapper,
        dbMapper: FeedDbMapper,
        dispatcher: DispatcherProvider
    ): FeedRepository =
        FeedDataRepository(
            feedRemoteDataSource,
            feedLocalDataSource,
            apiMapper,
            dbMapper,
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

    @Provides
    fun provideFeedDatabase(context: Context) = FeedDatabase.getInstance(context)
}
