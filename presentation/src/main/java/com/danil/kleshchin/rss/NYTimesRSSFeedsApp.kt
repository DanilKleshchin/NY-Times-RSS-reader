package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.*
import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.di.modules.FeedsListModule
import com.danil.kleshchin.rss.di.modules.SectionModule
import com.danil.kleshchin.rss.screens.feed.FeedNavigator
import com.danil.kleshchin.rss.screens.feedslist.FeedsListNavigator
import com.danil.kleshchin.rss.screens.sections.SectionNavigator
import kotlinx.coroutines.Dispatchers

class NYTimesRSSFeedsApp : Application() {

    private lateinit var sectionComponent: SectionComponent
    private lateinit var feedsListComponent: FeedsListComponent
    private lateinit var feedComponent: FeedComponent

    fun initSectionComponent(sectionNavigator: SectionNavigator) {
        sectionComponent = DaggerSectionComponent.builder()
            .sectionModule(SectionModule(sectionNavigator))
            .build()
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

    fun getSectionComponent() = sectionComponent

    fun getFeedsListComponent() = feedsListComponent

    fun getFeedComponent() = feedComponent

    //check this for date and time displaying https://www.rockandnull.com/java-time-android/
}
