package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import com.danil.kleshchin.rss.entities.section.SectionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedsListPresenter(
    private val getFeedBySectionUseCase: GetFeedBySectionUseCase,
    private val feedMapper: FeedMapper,
    private val feedsListNavigator: FeedsListNavigator
) : FeedsListContract.Presenter {

    private var feedsListView: FeedsListContract.View? = null
    private var feedList: List<FeedEntity> = emptyList()

    private lateinit var section: SectionEntity

    override fun setView(view: FeedsListContract.View) {
        feedsListView = view
    }

    override fun onAttach() {
        feedsListView?.showLoadingView()
    }

    override fun initialize(section: SectionEntity) {
        this.section = section
        feedsListView?.showSectionName(section.displayName)
        loadFeedsList()
    }

    override fun onDetach() {
        feedsListView = null
    }

    override fun onFeedSelected(feed: FeedEntity) {
        feedsListNavigator.navigateToFeedView(feed)
    }

    override fun onRefreshSelected() {
        loadFeedsList()
    }

    private fun loadFeedsList() {
        val uiScope = CoroutineScope(Dispatchers.Main)

        val params = GetFeedBySectionUseCase.Params(section.toSection().name)
        uiScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val currentTime = System.currentTimeMillis()
                feedList =
                    feedMapper.transform(getFeedBySectionUseCase.execute(params), currentTime)
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
