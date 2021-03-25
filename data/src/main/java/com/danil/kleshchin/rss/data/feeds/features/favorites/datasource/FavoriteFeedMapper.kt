package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity
import com.danil.kleshchin.rss.domain.entity.Feed
import javax.inject.Inject

class FavoriteFeedMapper @Inject constructor() {

    fun transformToDomain(feed: FavoriteFeedEntity): Feed =
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
            iconCopyright = feed.iconCopyright,
            isFavorite = feed.isFavorite
        )


    fun transformToDb(feed: Feed): FavoriteFeedEntity =
        FavoriteFeedEntity(
            id = feed.id,
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
            iconCopyright = feed.iconCopyright,
            isFavorite = feed.isFavorite
        )
}
