package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.DaggerFeedsListComponent
import com.danil.kleshchin.rss.di.components.DaggerSectionComponent
import com.danil.kleshchin.rss.di.components.FeedsListComponent
import com.danil.kleshchin.rss.di.components.SectionComponent
import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedsListModule
import com.danil.kleshchin.rss.di.modules.SectionModule
import com.danil.kleshchin.rss.screens.feedslist.FeedsListNavigator
import com.danil.kleshchin.rss.screens.sections.SectionNavigator
import kotlinx.coroutines.Dispatchers

class NYTimesRSSFeedsApp : Application() {

    private lateinit var sectionComponent: SectionComponent
    private lateinit var feedsListComponent: FeedsListComponent

    fun initSectionComponent(sectionNavigator: SectionNavigator) {
        sectionComponent = DaggerSectionComponent.builder()
            .sectionModule(SectionModule(sectionNavigator))
            .build()
    }

    fun initFeedComponent(feedsListNavigator: FeedsListNavigator) {
        feedsListComponent = DaggerFeedsListComponent.builder()
            .appModule(AppModule(Dispatchers))
            .feedModule(FeedsListModule(feedsListNavigator))
            .build()
    }

    fun getSectionComponent() = sectionComponent

    fun getFeedComponent() = feedsListComponent

    //check this for date and time displaying https://www.rockandnull.com/java-time-android/
}
