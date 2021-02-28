package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.screens.feed.FeedFragment
import dagger.Component

@Component(modules = [FeedModule::class])
interface FeedComponent {

    fun inject(feedsListFragment: FeedFragment)
}
