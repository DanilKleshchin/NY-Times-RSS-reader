package com.danil.kleshchin.rss.di.modules

import android.content.Context
import com.danil.kleshchin.rss.data.feeds.features.favorites.FavoriteFeedDataRepository
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedDatabase
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedLocalDataSource
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedLocalDataSourceImpl
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.FavoriteFeedMapper
import com.danil.kleshchin.rss.domain.interactor.features.favorites.FavoriteFeedRepository
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class FeedModule {

    @Provides
    fun provideFavoriteLocalDataSource(database: FavoriteFeedDatabase): FavoriteFeedLocalDataSource =
        FavoriteFeedLocalDataSourceImpl(database)

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
    fun provideFavoriteFeedDatabase(context: Context) = FavoriteFeedDatabase.getInstance(context)
}
