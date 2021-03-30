package com.danil.kleshchin.rss.domain

import com.danil.kleshchin.rss.domain.entity.Feed
import org.junit.Assert
import org.junit.Test

const val TITLE = "Interesting feed title"
const val DESCRIPTION = "Interesting feed description"
const val PAGE_URL = "google.com"
const val AUTHOR = "author"
const val DATE_CREATED = 1609380022000L
const val DATE_UPDATED_1 = 1611815251000L
const val DATE_UPDATED_2 = 1611815251050L
const val KICKER = "Forbes"
const val THUMB_URL = "google.com"
const val ICON_URL = "google.com"
const val ICON_CAPTION = "Feed icon caption"
const val ICON_COPYRIGHT = "NYT"
const val IS_FAVORITE_1 = false
const val IS_FAVORITE_2 = true

class FeedTest {

    private val feed1 = Feed(
        title = TITLE,
        description = DESCRIPTION,
        pageUrl = PAGE_URL,
        author = AUTHOR,
        dateCreated = DATE_CREATED,
        dateUpdated = DATE_UPDATED_1,
        kicker = KICKER,
        thumbUrl = THUMB_URL,
        iconUrl = ICON_URL,
        iconCaption = ICON_CAPTION,
        iconCopyright = ICON_COPYRIGHT,
        isFavorite = IS_FAVORITE_1
    )

    private val feed2 = Feed(
        title = TITLE,
        description = DESCRIPTION,
        pageUrl = PAGE_URL,
        author = AUTHOR,
        dateCreated = DATE_CREATED,
        dateUpdated = DATE_UPDATED_2,
        kicker = KICKER,
        thumbUrl = THUMB_URL,
        iconUrl = ICON_URL,
        iconCaption = ICON_CAPTION,
        iconCopyright = ICON_COPYRIGHT,
        isFavorite = IS_FAVORITE_2
    )

    @Test
    fun test_equals() {
        Assert.assertEquals(feed1, feed2)
    }
}
