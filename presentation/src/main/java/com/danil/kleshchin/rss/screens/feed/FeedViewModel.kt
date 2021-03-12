package com.danil.kleshchin.rss.screens.feed

import androidx.lifecycle.ViewModel
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.utils.ResourceHelper
import javax.inject.Inject

class FeedViewModel : ViewModel() {

    @Inject
    lateinit var resourceHelper: ResourceHelper

    lateinit var feed: FeedEntity //TODO checkout data passing between viewModels

    val title
        get() = feed.title
    val kicker
        get() = feed.kicker
    val author
        get() = feed.author
    val iconUrl
        get() = feed.iconUrl
    val iconCaption
        get() = feed.iconCaption
    val iconCopyright
        get() = feed.iconCopyright
    val description
        get() = feed.description
    val dateCreated
        get() = resourceHelper.getDateCreated(feed.dateCreated)
    val dateUpdated
        get() = resourceHelper.getDateUpdated(feed.dateUpdated)

    fun addFeedToFavourites() {
        //TODO save feed in a new db table via use case
    }
}
