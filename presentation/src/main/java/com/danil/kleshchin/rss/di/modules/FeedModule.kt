package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.data.feeds.FeedDataRepository
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import com.danil.kleshchin.rss.screens.feeds.FeedContract
import com.danil.kleshchin.rss.screens.feeds.FeedPresenter
import com.danil.kleshchin.rss.screens.feeds.Navigator
import dagger.Module
import dagger.Provides

@Module
class FeedModule(private val navigator: Navigator) {

    @Provides
    fun provideFeedPresenter(
        getFeedBySectionUseCase: GetFeedBySectionUseCase
    ): FeedContract.Presenter = FeedPresenter(getFeedBySectionUseCase, navigator)

    @Provides
    fun provideFeedRepository(): FeedRepository = FeedDataRepository()
}
