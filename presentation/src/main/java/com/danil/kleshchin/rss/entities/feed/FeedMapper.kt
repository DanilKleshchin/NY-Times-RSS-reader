package com.danil.kleshchin.rss.entities.feed

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import com.danil.kleshchin.elapsed_time.getDateTimeFromTimestamp
import com.danil.kleshchin.elapsed_time.getElapsedTimeString
import com.danil.kleshchin.elapsed_time.getTimestampFromDateTime
import com.danil.kleshchin.elapsed_time.pattern_1
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedMapper @Inject constructor() {

    fun transform(feedList: List<Feed>, currentTime: Long, resources: Resources): List<FeedEntity> {
        val feedEntityList = arrayListOf<FeedEntity>()
        for (feed in feedList) {
            feedEntityList.add(transform(feed, currentTime, resources))
        }
        return feedEntityList
    }

    fun transform(feedEntity: FeedEntity): Feed {
        return Feed(
            title = feedEntity.title,
            description = feedEntity.description,
            pageUrl = feedEntity.pageUrl,
            author = feedEntity.author,
            dateCreated = getTimestampFromDateTime(feedEntity.dateCreated, pattern_1), //TODO probably should store the long values in feedEntity too?
            dateUpdated = getTimestampFromDateTime(feedEntity.dateUpdated, pattern_1),
            kicker = feedEntity.kicker,
            thumbUrl = feedEntity.thumbUrl,
            iconUrl = feedEntity.iconUrl,
            iconCaption = feedEntity.iconCaption,
            iconCopyright = feedEntity.iconCopyright,
            isFavorite = feedEntity.isFavorite.get()
        )
    }

    private fun transform(feed: Feed, currentTime: Long, resources: Resources): FeedEntity {
        return FeedEntity(
            id = feed.id,
            title = feed.title,
            description = feed.description,
            pageUrl = feed.pageUrl,
            author = feed.author,
            timeElapsed = getElapsedTimeString(feed.dateCreated, currentTime, resources),
            dateCreated = getDateTimeFromTimestamp(feed.dateCreated, pattern_1),
            dateUpdated = getDateTimeFromTimestamp(feed.dateUpdated, pattern_1),
            kicker = feed.kicker,
            thumbUrl = feed.thumbUrl,
            iconUrl = feed.iconUrl,
            iconCaption = feed.iconCaption,
            iconCopyright = feed.iconCopyright,
            isFavorite = ObservableBoolean(feed.isFavorite)
        )
    }
}
