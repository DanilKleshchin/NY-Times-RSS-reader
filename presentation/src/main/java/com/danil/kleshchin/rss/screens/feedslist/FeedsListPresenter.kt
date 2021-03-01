package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import kotlinx.coroutines.*

class FeedsListPresenter(
    private val getFeedBySectionUseCase: GetFeedBySectionUseCase,
    private val feedsListNavigator: FeedsListNavigator
) : FeedsListContract.Presenter {

    private var feedsListView: FeedsListContract.View? = null
    private var feedList: List<Feed> = emptyList()

    private lateinit var section: Section

    override fun setView(view: FeedsListContract.View) {
        feedsListView = view
    }

    override fun onAttach() {
        feedsListView?.showLoadingView()
    }

    override fun initialize(section: Section) {
        this.section = section
        feedsListView?.showSectionName(section.displayName)
        loadFeedsList()
    }

    override fun onDetach() {
        feedsListView = null
    }

    override fun onFeedSelected(feed: Feed) {
        feedsListNavigator.navigateToFeedView(feed)
    }

    override fun onRefreshSelected() {
        loadFeedsList()
    }

    private fun loadFeedsList() {
        val uiScope = CoroutineScope(Dispatchers.Main)

        val params = GetFeedBySectionUseCase.Params(section.name)
        uiScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                feedList = getFeedBySectionUseCase.execute(params)
                withContext(Dispatchers.Main) {
                    feedsListView?.let {
                        it.showFeedList(feedList)
                        it.hideLoadingView()
                    }
                }
            }
        }
    }
}
