package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.entities.section.SectionEntity

interface SectionContract {

    interface View {
        fun showSectionList(sectionList: List<SectionEntity>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
    }

    interface Presenter: BasePresenter<View> {
        fun onSectionSelected(section: SectionEntity)
    }
}
