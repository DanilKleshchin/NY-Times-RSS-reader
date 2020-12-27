package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.di.modules.SectionModule
import com.danil.kleshchin.rss.screens.sections.SectionFragment
import dagger.Component

@Component(modules = [SectionModule::class])
interface SectionComponent {

    fun inject(sectionFragment: SectionFragment)
}
