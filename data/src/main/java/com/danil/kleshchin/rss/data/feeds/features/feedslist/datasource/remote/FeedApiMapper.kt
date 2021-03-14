package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedResultApiEntity
import com.danil.kleshchin.rss.data.feeds.utils.getTimeStampFromDateTime
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedApiMapper @Inject constructor() {

    fun transform(feedObject: FeedObjectApiEntity): List<Feed> {
        return transform(feedObject.results)
    }

    private fun transform(results: List<FeedResultApiEntity>): List<Feed> {
        val list = ArrayList<Feed>()

        for (result in results) {
            list.add(
                Feed(
                    title = result.title,
                    description = result.abstract,
                    pageUrl = result.url,
                    author = result.byline,
                    dateCreated = getTimeStampFromDateTime(result.dateCreated),
                    dateUpdated = getTimeStampFromDateTime(result.dateUpdated),
                    kicker = result.kicker?: UNKNOWN_DATA,
                    thumbUrl = getThumbUrlObject(result),
                    iconUrl = getIconUrlObject(result),
                    iconCaption = result.multimedia?.get(0)?.caption ?: UNKNOWN_DATA,
                    iconCopyright = result.multimedia?.get(0)?.copyright ?: UNKNOWN_DATA
                )
            )
        }
        return list
    }

    private fun getThumbUrlObject(result: FeedResultApiEntity): String {
        if (result.multimedia == null) return UNKNOWN_DATA

        for (multimedia in result.multimedia) {
            if (multimedia.format == THUMB_FORMAT) {
                return multimedia.url
            }
        }
        return UNKNOWN_DATA
    }

    private fun getIconUrlObject(result: FeedResultApiEntity): String {
        if (result.multimedia == null) return UNKNOWN_DATA

        for (multimedia in result.multimedia) {
            if (multimedia.format == ICON_FORMAT) {
                return multimedia.url
            }
        }
        return UNKNOWN_DATA
    }
}
