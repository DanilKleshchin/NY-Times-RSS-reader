package com.danil.kleshchin.rss.di.modules

import android.content.Context
import com.danil.kleshchin.rss.data.feeds.FeedDataRepository
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedDatabase
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.local.FeedLocalDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.datasource.network.BASE_URL
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApi
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.datasource.network.FeedRemoteDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.repository.FeedRepository
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
