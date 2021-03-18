package com.danil.kleshchin.rss.di.modules

import android.content.Context
import com.danil.kleshchin.rss.BuildConfig
import com.danil.kleshchin.rss.data.feeds.features.feedslist.FeedDataRepository
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDatabase
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedLocalDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.API_KEY_PARAM
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.API_TIMEOUT_SECONDS
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.BASE_URL
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApi
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSource
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedRemoteDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [AppModule::class, FeedModule::class])
class FeedsListModule(
    private val dispatcher: Dispatchers
) {

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

    @Provides
    fun provideDispatchers() = DispatcherProvider(
        database = dispatcher.IO,
        main = dispatcher.Main,
        network = dispatcher.Default
    )

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
            .build()
    }
}
