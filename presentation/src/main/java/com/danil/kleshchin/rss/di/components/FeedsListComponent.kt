package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.FeedsListModule
import com.danil.kleshchin.rss.screens.feedslist.FeedsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FeedsListModule::class])
interface FeedsListComponent {
    fun inject(feedsListViewModel: FeedsListViewModel)
}
