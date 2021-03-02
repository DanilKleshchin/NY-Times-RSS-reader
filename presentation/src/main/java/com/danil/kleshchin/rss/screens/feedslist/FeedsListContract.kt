package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.BasePresenter
import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.screens.sections.entities.SectionEntity

interface FeedsListContract {

    interface View {
        fun showSectionName(sectionName: String)
        fun showFeedList(feedList: List<Feed>)
        fun showLoadingView()
        fun hideLoadingView()
        fun showErrorMessage()
        fun showRetry()
        fun hideRetry()
    }

    interface Presenter:BasePresenter<View> {
        fun initialize(section: SectionEntity)
        fun onRefreshSelected()
        fun onFeedSelected(feed: Feed)
    }
}
