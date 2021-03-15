package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.screens.favorites.FavoriteFeedsListViewModel
import dagger.Component

@Component(modules = [FeedModule::class])
interface FavoriteFeedsListComponent {
    fun inject(favoriteFeedsListViewModel: FavoriteFeedsListViewModel)
}
