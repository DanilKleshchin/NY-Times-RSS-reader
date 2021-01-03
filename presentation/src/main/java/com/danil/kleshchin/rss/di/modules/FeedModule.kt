package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.data.feeds.FeedDataRepository
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import com.danil.kleshchin.rss.screens.feeds.FeedContract
import com.danil.kleshchin.rss.screens.feeds.FeedPresenter
import com.danil.kleshchin.rss.screens.feeds.FeedNavigator
import dagger.Module
import dagger.Provides

@Module
class FeedModule(private val feedNavigator: FeedNavigator) {

    @Provides
    fun provideFeedPresenter(
        getFeedBySectionUseCase: GetFeedBySectionUseCase
    ): FeedContract.Presenter = FeedPresenter(getFeedBySectionUseCase, feedNavigator)

    @Provides
    fun provideFeedRepository(): FeedRepository = FeedDataRepository()
}
