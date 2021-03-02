package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.domain.interactor.section.GetSectionListUseCase
import com.danil.kleshchin.rss.screens.sections.entities.SectionEntity
import com.danil.kleshchin.rss.screens.sections.entities.SectionMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SectionPresenter(
    private val getSectionListUseCase: GetSectionListUseCase,
    private val sectionNavigator: SectionNavigator,
    private val sectionMapper: SectionMapper
): SectionContract.Presenter {

    private var sectionView: SectionContract.View? = null
    private var sectionList: List<SectionEntity> = emptyList()

    override fun setView(view: SectionContract.View) {
        sectionView = view
    }

    override fun onAttach() {
        sectionView?.showLoadingView()
        executeGetSectionListUseCase()
    }

    private fun executeGetSectionListUseCase() {
        GlobalScope.launch(Dispatchers.Main) {
            sectionList = sectionMapper.transform(getSectionListUseCase.execute(Unit))
            sectionView?.showSectionList(sectionList)
            sectionView?.hideLoadingView()
        }
    }

    override fun onDetach() {
        sectionView = null
    }

    override fun onSectionSelected(section: SectionEntity) {
        sectionNavigator.showFeedView(section)
    }
}
