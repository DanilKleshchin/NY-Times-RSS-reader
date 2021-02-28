package com.danil.kleshchin.rss.screens.feed

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.entity.Section

interface FeedContract {

    interface View {
        fun showFeed(feed: Feed)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
        fun showRetry()
        fun hideRetry()
    }

    interface Presenter:BasePresenter<View> {
        fun initialize(feed: Feed)
        fun onReadFullArticleSelected(feed: Feed)
    }
}
