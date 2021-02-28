package com.danil.kleshchin.rss.screens.feed

import com.danil.kleshchin.rss.domain.entity.Feed

class FeedPresenter(
    private val feedNavigator: FeedNavigator
) : FeedContract.Presenter {

    private var feedView: FeedContract.View? = null

    override fun setView(view: FeedContract.View) {
        feedView = view
    }

    override fun onAttach() {
        feedView?.showLoadingView()
    }

    override fun initialize(feed: Feed) {
        feedView?.showFeed(feed)
    }

    override fun onDetach() {
        feedView = null
    }

    override fun onReadFullArticleSelected(feed: Feed) {
        feedNavigator.showWebPage(feed.feedPageUrl)
    }
}
