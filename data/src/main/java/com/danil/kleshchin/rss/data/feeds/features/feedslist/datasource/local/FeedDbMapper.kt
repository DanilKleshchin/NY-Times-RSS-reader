package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedDbMapper @Inject constructor() {

    fun transform(feedDbEntity: FeedDbEntity): Feed =
        Feed(
            title = feedDbEntity.title,
            description = feedDbEntity.description,
            pageUrl = feedDbEntity.pageUrl,
            author = feedDbEntity.author,
            dateCreated = feedDbEntity.dateCreated,
            dateUpdated = feedDbEntity.dateUpdated,
            kicker = feedDbEntity.kicker,
            thumbUrl = feedDbEntity.thumbUrl,
            iconUrl = feedDbEntity.iconUrl,
            iconCaption = feedDbEntity.iconCaption,
            iconCopyright = feedDbEntity.iconCopyright
        )

    fun transform(sectionName: String, position: Int, feed: Feed): FeedDbEntity =
        FeedDbEntity(
            id = feed.id,
            position = position,
            sectionName = sectionName,
            title = feed.title,
            description = feed.description,
            pageUrl = feed.pageUrl,
            author = feed.author,
            dateCreated = feed.dateCreated,
            dateUpdated = feed.dateUpdated,
            kicker = feed.kicker,
            thumbUrl = feed.thumbUrl,
            iconUrl = feed.iconUrl,
            iconCaption = feed.iconCaption,
            iconCopyright = feed.iconCopyright
        )
}
