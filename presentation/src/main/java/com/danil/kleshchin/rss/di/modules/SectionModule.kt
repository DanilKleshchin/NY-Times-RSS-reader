package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.domain.interactor.section.GetSectionListUseCase
import com.danil.kleshchin.rss.screens.sections.SectionNavigator
import com.danil.kleshchin.rss.screens.sections.SectionContract
import com.danil.kleshchin.rss.screens.sections.SectionPresenter
import dagger.Module
import dagger.Provides

@Module
class SectionModule(private val sectionNavigator: SectionNavigator) {

    @Provides
    fun provideSectionPresenter(
        getSectionListUseCase: GetSectionListUseCase
    ): SectionContract.Presenter = SectionPresenter(getSectionListUseCase, sectionNavigator)
}
