package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.DaggerFeedsListComponent
import com.danil.kleshchin.rss.di.components.DaggerSectionComponent
import com.danil.kleshchin.rss.di.components.FeedsListComponent
import com.danil.kleshchin.rss.di.components.SectionComponent
import com.danil.kleshchin.rss.di.modules.AppModule
import com.danil.kleshchin.rss.di.modules.FeedsListModule
import kotlinx.coroutines.Dispatchers

class NYTimesRSSFeedsApp : Application() {

    val sectionComponent: SectionComponent
        get() = DaggerSectionComponent.builder().build()

    val feedsListComponent: FeedsListComponent
        get() = DaggerFeedsListComponent.builder()
            .appModule(AppModule(Dispatchers))
            .feedsListModule(FeedsListModule())
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
