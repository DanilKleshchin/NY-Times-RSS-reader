package com.danil.kleshchin.rss

import android.app.Application
import com.danil.kleshchin.rss.di.components.DaggerSectionComponent
import com.danil.kleshchin.rss.di.components.SectionComponent
import com.danil.kleshchin.rss.di.modules.SectionModule
import com.danil.kleshchin.rss.screens.sections.Navigator

class NYTimesRSSFeedsApp : Application() {

    private lateinit var sectionComponent: SectionComponent

    fun initSectionComponent(navigator: Navigator) {
        sectionComponent = DaggerSectionComponent.builder()
            .sectionModule(SectionModule(navigator))
            .build()
    }

    fun getSectionComponent() = sectionComponent
}
