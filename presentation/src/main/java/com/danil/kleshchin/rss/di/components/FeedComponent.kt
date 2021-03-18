package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.screens.feed.FeedViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FeedModule::class])
interface FeedComponent {
    fun inject(viewModel: FeedViewModel)
}
