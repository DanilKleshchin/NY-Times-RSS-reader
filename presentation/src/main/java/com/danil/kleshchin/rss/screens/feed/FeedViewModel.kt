package com.danil.kleshchin.rss.screens.feed

import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.screens.BaseFeedViewModel

class FeedViewModel : BaseFeedViewModel() {

    lateinit var feed: FeedEntity

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
    val isFavorite
        get() = feed.isFavorite
}
