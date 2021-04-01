package com.danil.kleshchin.rss.di.modules

import android.content.Context
import com.danil.kleshchin.rss.data.feeds.features.favorites.FavoriteFeedDataRepository
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedDatabase
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedMapper
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class FeedModule {

    @Provides
    fun provideFavoriteFeedRepository(
        localDataSource: FavoriteFeedLocalDataSource,
        mapper: FavoriteFeedMapper
    ): FavoriteFeedRepository =
        FavoriteFeedDataRepository(
            localDataSource,
            mapper
        )

    @Provides
    @Singleton
    fun provideFavoriteFeedDatabase(context: Context) = FavoriteFeedDatabase.getInstance(context)
}
