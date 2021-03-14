package com.danil.kleshchin.rss.screens.sections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases.GetSectionListUseCase
import com.danil.kleshchin.rss.entities.section.SectionMapper
import javax.inject.Inject

class SectionViewModel : ViewModel() {

    @Inject
    lateinit var getSectionListUseCase: GetSectionListUseCase

    @Inject
    lateinit var sectionMapper: SectionMapper

    val sections =
        liveData {
            emit(
                sectionMapper.transform(getSectionListUseCase.execute(Unit))
            )
        }
}
