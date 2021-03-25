package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedResultApiEntity
import com.danil.kleshchin.rss.data.feeds.utils.getTimeStampFromDateTime
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedApiMapper @Inject constructor() {

    fun transform(result: FeedResultApiEntity): Feed {
        return Feed(
            title = result.title,
            description = result.abstract,
            pageUrl = result.url,
            author = result.byline,
            dateCreated = getTimeStampFromDateTime(result.dateCreated),
            dateUpdated = getTimeStampFromDateTime(result.dateUpdated),
            kicker = result.kicker ?: UNKNOWN_DATA,
            thumbUrl = getThumbUrlObject(result),
            iconUrl = getIconUrlObject(result),
            iconCaption = result.multimedia?.get(0)?.caption ?: UNKNOWN_DATA,
            iconCopyright = result.multimedia?.get(0)?.copyright ?: UNKNOWN_DATA
        )
    }

    private fun getThumbUrlObject(result: FeedResultApiEntity): String {
        return result.multimedia?.firstOrNull { it.format == THUMB_FORMAT }?.url ?: UNKNOWN_DATA
    }

    private fun getIconUrlObject(result: FeedResultApiEntity): String {
        return result.multimedia?.firstOrNull { it.format == ICON_FORMAT }?.url ?: UNKNOWN_DATA
    }
}
