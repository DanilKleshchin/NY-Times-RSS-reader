package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.entity.Section
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import kotlinx.coroutines.*

class FeedsListPresenter(
    private val getFeedBySectionUseCase: GetFeedBySectionUseCase,
    private val feedsListNavigator: FeedsListNavigator
) : FeedsListContract.Presenter {

    private lateinit var feedsListView: FeedsListContract.View
    private var feedList: List<Feed> = emptyList()

    override fun setView(view: FeedsListContract.View) {
        feedsListView = view
    }

    override fun onAttach() {
        feedsListView.showLoadingView()
    }

    override fun initialize(section: Section) {
        feedsListView.showSectionName(section.displayName)

        val uiScope = CoroutineScope(Dispatchers.Main)

        val params = GetFeedBySectionUseCase.Params(section.name)
        uiScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                feedList = getFeedBySectionUseCase.execute(params)
                withContext(Dispatchers.Main) {
                    feedsListView.showFeedList(feedList)
                }
            }
        }

        /*getFeedBySectionUseCase.execute(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { feeds ->
                    feedList = feeds
                    feedView.showFeedList(feedList)
                },
                { th ->
                    th.printStackTrace()
                    feedView.showErrorMessage()
                },
                {
                    feedView.hideLoadingView()
                }
            )*/
    }

    override fun onDetach() {
        //TODO think about setting view null value here
    }

    override fun onFeedSelected(feed: Feed) {
        //feedNavigator.showWebPage(feed.fulFeedPageUrl)
    }
}
