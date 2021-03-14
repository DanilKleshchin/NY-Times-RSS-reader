package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FeedDbMapper @Inject constructor() {

    fun transformToDomain(list: List<FeedDbEntity>): List<Feed> {
        val feedList = ArrayList<Feed>()
        list.forEach { feed ->
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

    fun transformToDb(sectionName: String, list: List<Feed>): List<FeedDbEntity> {
        val feedList = ArrayList<FeedDbEntity>()
        list.forEach { feed ->
            feedList.add(
                FeedDbEntity(
                    id = feed.id,
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
            )
        }
        return feedList
    }
}
