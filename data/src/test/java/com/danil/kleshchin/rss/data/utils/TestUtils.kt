package com.danil.kleshchin.rss.data.utils

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.ICON_FORMAT
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.THUMB_FORMAT
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedMultimediaApiEntity
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedResultApiEntity
import com.danil.kleshchin.rss.domain.entity.Feed

const val TITLE = "Interesting feed title"
const val DESCRIPTION = "Interesting feed description"
const val PAGE_URL = "google.com"
const val AUTHOR = "author"
const val DATE_CREATED_LONG = 1609380022000L
const val DATE_UPDATED_LONG = 1611815251000L
const val DATE_CREATED_STRING = "2020-12-31T05:00:22-05:00"
const val DATE_UPDATED_STRING = "2021-01-28T09:27:31-05:00"
const val KICKER = "Forbes"
const val THUMB_URL = "google.com"
const val ICON_URL = "google.com"
const val ICON_CAPTION = "Feed icon caption"
const val ICON_COPYRIGHT = "NYT"
const val IS_FAVORITE = false
const val IMAGE_TYPE = "image"
const val DB_POSITION = 1
const val SECTION_NAME = "Arts"

/**
 * [Feed] object used for tests.
 */
val testFeed = Feed(
    title = TITLE,
    description = DESCRIPTION,
    pageUrl = PAGE_URL,
    author = AUTHOR,
    dateCreated = DATE_CREATED_LONG,
    dateUpdated = DATE_UPDATED_LONG,
    kicker = KICKER,
    thumbUrl = THUMB_URL,
    iconUrl = ICON_URL,
    iconCaption = ICON_CAPTION,
    iconCopyright = ICON_COPYRIGHT,
    isFavorite = IS_FAVORITE
)

/**
 * [FeedObjectApiEntity] object used for tests.
 */
val testFeedApiObject = FeedObjectApiEntity(getFeedResultArr())

fun getFeedResultArr(): List<FeedResultApiEntity> {
    val testFeedResult = FeedResultApiEntity(
        title = TITLE,
        abstract = DESCRIPTION,
        url = PAGE_URL,
        byline = AUTHOR,
        kicker = KICKER,
        multimedia = getMultimediaArr(),
        dateCreated = DATE_CREATED_STRING,
        dateUpdated = DATE_UPDATED_STRING
    )
    val arr = ArrayList<FeedResultApiEntity>()
    arr.add(testFeedResult)
    return arr
}

fun getMultimediaArr(): List<FeedMultimediaApiEntity> {
    val icon = FeedMultimediaApiEntity(
        url = ICON_URL,
        format = ICON_FORMAT,
        type = IMAGE_TYPE,
        caption = ICON_CAPTION,
        copyright = ICON_COPYRIGHT
    )
    val thumb = FeedMultimediaApiEntity(
        url = THUMB_URL,
        format = THUMB_FORMAT,
        type = IMAGE_TYPE,
        caption = ICON_CAPTION,
        copyright = ICON_COPYRIGHT
    )
    val arr = ArrayList<FeedMultimediaApiEntity>()
    arr.add(icon)
    arr.add(thumb)
    return arr
}

/**
 * [FeedDbEntity] object used for tests.
 */
val testFeedDbEntity = FeedDbEntity(
    id = 0,
    position = DB_POSITION,
    sectionName = SECTION_NAME,
    title = TITLE,
    description = DESCRIPTION,
    pageUrl = PAGE_URL,
    author = AUTHOR,
    dateCreated = DATE_CREATED_LONG,
    dateUpdated = DATE_UPDATED_LONG,
    kicker = KICKER,
    thumbUrl = THUMB_URL,
    iconUrl = ICON_URL,
    iconCaption = ICON_CAPTION,
    iconCopyright = ICON_COPYRIGHT
)


