package com.danil.kleshchin.rss.data.feeds.datasource.local

import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedDbMapper @Inject constructor() {

    fun transformToDomain(list: List<FeedDbEntity>): List<Feed> {
        val feedList = ArrayList<Feed>()
        for (feed in feedList) {
            feedList.add(
                Feed(
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
            )
        }
        return feedList
    }

    fun transformToDb(list: List<Feed>): List<FeedDbEntity> {
        val feedList = ArrayList<FeedDbEntity>()
        for (feed in feedList) {
            feedList.add(
                FeedDbEntity(
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
            )
        }
        return feedList
    }
}
