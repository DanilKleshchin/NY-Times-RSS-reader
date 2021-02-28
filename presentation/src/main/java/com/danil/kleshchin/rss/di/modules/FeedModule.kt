package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.screens.feed.FeedContract
import com.danil.kleshchin.rss.screens.feed.FeedNavigator
import com.danil.kleshchin.rss.screens.feed.FeedPresenter
import dagger.Module
import dagger.Provides

@Module
class FeedModule(
    private val feedNavigator: FeedNavigator
) {

    @Provides
    fun provideFeedPresenter(): FeedContract.Presenter = FeedPresenter(feedNavigator)
}
