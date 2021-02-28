package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.section.GetSectionListUseCase
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SectionPresenter(
    private val getSectionListUseCase: GetSectionListUseCase,
    private val sectionNavigator: SectionNavigator
): SectionContract.Presenter {

    private var sectionView: SectionContract.View? = null
    private var sectionList: List<Section> = emptyList()

    override fun setView(view: SectionContract.View) {
        sectionView = view
    }

    override fun onAttach() {
        sectionView?.showLoadingView()
        executeGetSectionListUseCase()
    }

    private fun executeGetSectionListUseCase() {
        GlobalScope.launch(Dispatchers.Main) {
            sectionList = getSectionListUseCase.execute(Unit)
            sectionView?.showSectionList(sectionList)
            sectionView?.hideLoadingView()
        }
    }

    override fun onDetach() {
        sectionView = null
    }

    override fun onSectionSelected(section: Section) {
        sectionNavigator.showFeedView(section)
    }
}
