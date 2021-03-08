package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.DaggerFeedComponent
import com.danil.kleshchin.rss.di.components.DaggerFeedsListComponent
import com.danil.kleshchin.rss.di.components.DaggerSectionComponent
import com.danil.kleshchin.rss.di.components.FeedComponent
import com.danil.kleshchin.rss.di.components.FeedsListComponent
import com.danil.kleshchin.rss.di.components.SectionComponent
import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.di.modules.FeedsListModule
import com.danil.kleshchin.rss.screens.feed.FeedNavigator
import com.danil.kleshchin.rss.screens.feedslist.FeedsListNavigator
import kotlinx.coroutines.Dispatchers

class NYTimesRSSFeedsApp : Application() {

    private lateinit var feedsListComponent: FeedsListComponent
    private lateinit var feedComponent: FeedComponent

    val sectionComponent: SectionComponent
        get() = DaggerSectionComponent.builder().build()

    companion object {
        internal lateinit var INSTANCE: NYTimesRSSFeedsApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    fun initFeedsListComponent(feedsListNavigator: FeedsListNavigator) {
        feedsListComponent = DaggerFeedsListComponent.builder()
            .appModule(AppModule(Dispatchers))
            .feedsListModule(FeedsListModule(feedsListNavigator))
            .build()
    }

    fun initFeedComponent(feedNavigator: FeedNavigator) {
        feedComponent = DaggerFeedComponent.builder()
            .feedModule(FeedModule(feedNavigator))
            .build()
    }

    fun getFeedsListComponent() = feedsListComponent

    fun getFeedComponent() = feedComponent
}
