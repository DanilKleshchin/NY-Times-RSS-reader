package com.danil.kleshchin.rss.screens.sections

import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.section.GetSectionListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SectionPresenter(
    private val getSectionListUseCase: GetSectionListUseCase,
    private val sectionNavigator: SectionNavigator
): SectionContract.Presenter {

    private lateinit var sectionView: SectionContract.View
    private var sectionList: List<Section> = emptyList()

    override fun setView(view: SectionContract.View) {
        sectionView = view
    }

    override fun onAttach() {
        sectionView.showLoadingView()
        executeGetSectionListUseCase()
    }

    private fun executeGetSectionListUseCase() {
        getSectionListUseCase.execute(Unit)
            .subscribeOn(Schedulers.io()) // TODO try here observerOn(Schedulers.io()). Will it crash or not?
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        sections ->
                    sectionList = sections
                    sectionView.showSectionList(sectionList)
                    sectionView.hideLoadingView()
                },
                {
                        th ->
                    th.printStackTrace()
                    sectionView.showErrorMessage()
                },
                {
                    sectionView.hideLoadingView()
                }
            )
    }

    override fun onDetach() {

    }

    override fun onSectionSelected(section: Section) {
        sectionNavigator.showFeedView(section)
    }
}
