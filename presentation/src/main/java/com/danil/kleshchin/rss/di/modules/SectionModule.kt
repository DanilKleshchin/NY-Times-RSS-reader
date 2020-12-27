package com.danil.kleshchin.rss.di.modules

import com.danil.kleshchin.rss.domain.interactor.section.GetSectionListUseCase
import com.danil.kleshchin.rss.screens.sections.Navigator
import com.danil.kleshchin.rss.screens.sections.SectionContract
import com.danil.kleshchin.rss.screens.sections.SectionPresenter
import dagger.Module
import dagger.Provides

@Module
class SectionModule(private val navigator: Navigator) {

    @Provides
    fun provideSectionPresenter(
        getSectionListUseCase: GetSectionListUseCase
    ): SectionContract.Presenter = SectionPresenter(getSectionListUseCase, navigator)
}
