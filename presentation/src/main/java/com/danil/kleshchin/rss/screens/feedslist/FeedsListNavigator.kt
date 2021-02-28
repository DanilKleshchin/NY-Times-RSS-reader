package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.domain.entity.Feed

interface FeedsListNavigator {

    fun navigateToFeedView(feed: Feed)
}
