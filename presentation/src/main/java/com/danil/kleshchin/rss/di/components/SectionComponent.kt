package com.danil.kleshchin.rss.di.components

import com.danil.kleshchin.rss.screens.sections.SectionViewModel
import dagger.Component

@Component
interface SectionComponent {

    fun inject(sectionViewModel: SectionViewModel)
}
