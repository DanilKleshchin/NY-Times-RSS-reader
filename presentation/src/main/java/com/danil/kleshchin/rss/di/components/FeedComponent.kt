package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.screens.feed.FeedViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface FeedComponent {
    fun inject(viewModel: FeedViewModel)
}
