package com.danil.kleshchin.rss

import androidx.databinding.ObservableBoolean
import com.danil.kleshchin.rss.entities.feed.FeedEntity
import com.danil.kleshchin.rss.entities.feed.FeedMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test

const val ID = 596081288
const val TITLE = "Interesting feed title"
const val DESCRIPTION = "Interesting feed description"
const val PAGE_URL = "google.com"
const val AUTHOR = "author"
const val DATE_CREATED_LONG = 1609390822000L
const val DATE_UPDATED_LONG = 1611826051000L
const val DATE_CREATED_STRING = "31-12-2020 05:00:22"
const val DATE_UPDATED_STRING = "28-01-2021 09:27:31"
const val KICKER = "Forbes"
const val THUMB_URL = "google.com"
const val ICON_URL = "google.com"
const val ICON_CAPTION = "Feed icon caption"
const val ICON_COPYRIGHT = "NYT"
const val IS_FAVORITE = false

class FeedMapperTest {

    private lateinit var mapper: FeedMapper
    private lateinit var feedEntity: FeedEntity

    @Before
    fun setUp() {
        mapper = FeedMapper()
        initFeedEntity()
    }

    @Test
    fun test_from_feed_entity_to_feed() {
        val actualFeed = mapper.transform(feedEntity)

        Assert.assertEquals(feedEntity.title, actualFeed.title)
        Assert.assertEquals(feedEntity.description, actualFeed.description)
        Assert.assertEquals(feedEntity.pageUrl, actualFeed.pageUrl)
        Assert.assertEquals(feedEntity.author, actualFeed.author)
        Assert.assertEquals(DATE_CREATED_LONG, actualFeed.dateCreated)
        Assert.assertEquals(DATE_UPDATED_LONG, actualFeed.dateUpdated)
        Assert.assertEquals(feedEntity.kicker, actualFeed.kicker)
        Assert.assertEquals(feedEntity.thumbUrl, actualFeed.thumbUrl)
        Assert.assertEquals(feedEntity.iconUrl, actualFeed.iconUrl)
        Assert.assertEquals(feedEntity.iconCaption, actualFeed.iconCaption)
        Assert.assertEquals(feedEntity.iconCopyright, actualFeed.iconCopyright)
        Assert.assertEquals(feedEntity.isFavorite.get(), actualFeed.isFavorite)
    }

    private fun initFeedEntity() {
        feedEntity = FeedEntity(
            id = ID,
            title = TITLE,
            description = DESCRIPTION,
            pageUrl = PAGE_URL,
            author = AUTHOR,
            timeElapsed = "",
            dateCreated = DATE_CREATED_STRING,
            dateUpdated = DATE_UPDATED_STRING,
            kicker = KICKER,
            thumbUrl = THUMB_URL,
            iconUrl = ICON_URL,
            iconCaption = ICON_CAPTION,
            iconCopyright = ICON_COPYRIGHT,
            isFavorite = ObservableBoolean(IS_FAVORITE)
        )
    }
}
