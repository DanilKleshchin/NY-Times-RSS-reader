package com.danil.kleshchin.rss.screens.feed

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.entities.feed.FeedEntity

interface FeedContract {

    interface View {
        fun showFeed(feed: FeedEntity)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
        fun showRetry()
        fun hideRetry()
    }

    interface Presenter:BasePresenter<View> {
        fun initialize(feed: FeedEntity)
        fun onReadFullArticleSelected(feed: FeedEntity)
    }
}
