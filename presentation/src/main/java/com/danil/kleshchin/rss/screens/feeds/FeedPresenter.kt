package com.danil.kleshchin.rss.screens.feeds

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.feed.GetFeedBySectionUseCase
import com.danil.kleshchin.rss.screens.feeds.model.FeedModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedPresenter(
    private val getFeedBySectionUseCase: GetFeedBySectionUseCase,
    private val feedNavigator: FeedNavigator
) : FeedContract.Presenter {

    private lateinit var feedView: FeedContract.View
    private var feedList: List<Feed> = emptyList()

    override fun setView(view: FeedContract.View) {
        feedView = view
    }

    override fun onAttach() {
        feedView.showLoadingView()
    }

    override fun initialize(sectionName: String) {
        val params = GetFeedBySectionUseCase.Params(sectionName)
        getFeedBySectionUseCase.execute(params)
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
            )
    }

    override fun onDetach() {
        //TODO think about setting view null value here
    }

    override fun onFeedSelected(feed: Feed) {
        feedNavigator.showWebPage(feed.fulFeedPageUrl)
    }
}
