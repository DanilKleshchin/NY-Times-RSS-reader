package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.data.feeds.datasource.network.API_TIMEOUT_SECONDS
import com.danil.kleshchin.rss.data.feeds.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class AppModule(private val dispatcher: Dispatchers) {

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
            .build()
    }
}
