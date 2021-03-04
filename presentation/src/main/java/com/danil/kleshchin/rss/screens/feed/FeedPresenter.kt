package com.danil.kleshchin.rss.screens.feed

import com.danil.kleshchin.rss.entities.feed.FeedEntity

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

    override fun initialize(feed: FeedEntity) {
        feedView?.showFeed(feed)
    }

    override fun onDetach() {
        feedView = null
    }

    override fun onReadFullArticleSelected(feed: FeedEntity) {
        feedNavigator.showWebPage(feed.feedPageUrl)
    }
}
