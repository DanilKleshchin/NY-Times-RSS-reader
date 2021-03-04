package com.danil.kleshchin.rss.entities.feed

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.utils.getDateTimeFromTimeStamp
import com.danil.kleshchin.rss.utils.getElapsedTimeFromCurrentTime
import javax.inject.Inject

class FeedMapper @Inject constructor() {

    fun transform(feed: Feed, currentTime: Long): FeedEntity {
        return FeedEntity(
            title = feed.title,
            description = feed.description,
            feedPageUrl = feed.feedPageUrl,
            author = feed.author,
            timeElapsed = getElapsedTimeFromCurrentTime(feed.dateCreated, currentTime),
            dateCreated = getDateTimeFromTimeStamp(feed.dateCreated),
            dateUpdated = getDateTimeFromTimeStamp(feed.dateUpdated),
            kicker = feed.kicker,
            thumbUrl = feed.thumbUrl,
            iconUrl = feed.iconUrl,
            iconCaption = feed.iconCaption,
            iconCopyright = feed.iconCopyright
        )
    }

    fun transform(feedList: List<Feed>, currentTime: Long): List<FeedEntity> {
        val feedEntityList = arrayListOf<FeedEntity>()
        for (feed in feedList) {
            feedEntityList.add(transform(feed, currentTime))
        }
        return feedEntityList
    }
}
