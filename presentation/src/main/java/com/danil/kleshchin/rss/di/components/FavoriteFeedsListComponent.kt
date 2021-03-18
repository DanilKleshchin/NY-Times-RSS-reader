package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.screens.favorites.FavoriteFeedsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FeedModule::class])
interface FavoriteFeedsListComponent {
    fun inject(favoriteFeedsListViewModel: FavoriteFeedsListViewModel)
}
