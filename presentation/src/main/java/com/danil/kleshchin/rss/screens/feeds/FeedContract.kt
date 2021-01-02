package com.danil.kleshchin.rss.screens.feeds

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.screens.feeds.model.FeedModel

interface FeedContract {

    interface View {
        fun showFeedList(feedList: List<Feed>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
    }

    interface Presenter:BasePresenter<View> {
        fun onFeedSelected(feedModel: FeedModel)
    }
}
