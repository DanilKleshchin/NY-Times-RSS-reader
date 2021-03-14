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
import kotlinx.coroutines.Dispatchers

class NYTimesRSSFeedsApp : Application() {

    private val dispatcher by lazy { Dispatchers }
    private val appModule by lazy { AppModule(this, dispatcher) }
    private val feedsListModule by lazy { FeedsListModule() }
    private val feedModule by lazy { FeedModule(this) }

    val sectionComponent: SectionComponent
        get() = DaggerSectionComponent.builder().build()

    val feedsListComponent: FeedsListComponent
        get() = DaggerFeedsListComponent.builder()
            .appModule(appModule)
            .feedsListModule(feedsListModule)
            .build()

    val feedComponent: FeedComponent
        get() = DaggerFeedComponent.builder()
            .appModule(appModule)
            .feedModule(feedModule)
            .build()

    companion object {
        internal lateinit var INSTANCE: NYTimesRSSFeedsApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
