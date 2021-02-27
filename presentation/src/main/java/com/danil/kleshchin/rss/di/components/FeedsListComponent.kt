package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedsListModule
import com.danil.kleshchin.rss.screens.feedslist.FeedsListFragment
import dagger.Component

@Component(modules = [AppModule::class, FeedsListModule::class])
interface FeedsListComponent {

    fun inject(feedsListFragment: FeedsListFragment)
}
