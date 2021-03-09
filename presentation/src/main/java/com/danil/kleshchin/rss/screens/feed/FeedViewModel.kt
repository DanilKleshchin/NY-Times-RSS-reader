package com.danil.kleshchin.rss.screens.feed

import androidx.lifecycle.ViewModel
import com.danil.kleshchin.rss.entities.feed.FeedEntity

class FeedViewModel : ViewModel() {

    lateinit var feed: FeedEntity //TODO checkout data passing between viewModels

    fun addFeedToFavourites() {
        //TODO save feed in a new db table via use case
    }
}
