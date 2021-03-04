package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.section.SectionEntity

interface FeedsListContract {

    interface View {
        fun showSectionName(sectionName: String)
        fun showFeedList(feedList: List<FeedEntity>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
        fun showRetry()
        fun hideRetry()
    }

    interface Presenter : BasePresenter<View> {
        fun initialize(section: SectionEntity)
        fun onRefreshSelected()
        fun onFeedSelected(feed: FeedEntity)
    }
}
