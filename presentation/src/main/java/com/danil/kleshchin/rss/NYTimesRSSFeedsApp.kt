package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.DaggerFeedComponent
import com.danil.kleshchin.rss.di.components.DaggerSectionComponent
import com.danil.kleshchin.rss.di.components.FeedComponent
import com.danil.kleshchin.rss.di.components.SectionComponent
import com.danil.kleshchin.rss.di.modules.FeedModule
import com.danil.kleshchin.rss.di.modules.SectionModule
import com.danil.kleshchin.rss.screens.feeds.FeedNavigator
import com.danil.kleshchin.rss.screens.sections.SectionNavigator

class NYTimesRSSFeedsApp : Application() {

    private lateinit var sectionComponent: SectionComponent
    private lateinit var feedComponent: FeedComponent

    fun initSectionComponent(sectionNavigator: SectionNavigator) {
        sectionComponent = DaggerSectionComponent.builder()
            .sectionModule(SectionModule(sectionNavigator))
            .build()
    }

    fun initFeedComponent(feedNavigator: FeedNavigator) {
        feedComponent = DaggerFeedComponent.builder()
            .feedModule(FeedModule(feedNavigator))
            .build()
    }

    fun getSectionComponent() = sectionComponent

    fun getFeedComponent() = feedComponent
}
