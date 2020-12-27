package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.domain.entity.Section

interface SectionContract {

    interface View {
        fun showSectionList(sectionList: List<Section>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
    }

    interface Presenter: BasePresenter<View> {
        fun onSectionSelected(section: Section)
    }
}