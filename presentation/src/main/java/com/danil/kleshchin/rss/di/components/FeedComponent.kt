package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.screens.feeds.FeedFragment
import dagger.Component

@Component(modules = [FeedModule::class])
interface FeedComponent {

    fun inject(feedFragment: FeedFragment)
}
