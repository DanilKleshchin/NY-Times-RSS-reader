package com.danil.kleshchin.rss.screens.feedslist

import com.danil.kleshchin.rss.entities.feed.FeedEntity

interface FeedsListNavigator {

    fun navigateToFeedView(feed: FeedEntity)
}
