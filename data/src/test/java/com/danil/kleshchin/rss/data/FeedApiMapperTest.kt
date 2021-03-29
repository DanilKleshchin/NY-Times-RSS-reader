package com.danil.kleshchin.rss.data

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.FeedApiMapper
import com.danil.kleshchin.rss.data.utils.testFeed
import com.danil.kleshchin.rss.data.utils.testFeedApiObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FeedApiMapperTest {

    private lateinit var mapper: FeedApiMapper

    @Before
    fun setUp() {
        mapper = FeedApiMapper()
    }

    @Test
    fun transform_to_domain_test() {
        val actualDomain = mapper.transform(testFeedApiObject.results[0])

        assertEquals(testFeed.title, actualDomain.title)
        assertEquals(testFeed.description, actualDomain.description)
        assertEquals(testFeed.pageUrl, actualDomain.pageUrl)
        assertEquals(testFeed.author, actualDomain.author)
        assertEquals(testFeed.dateCreated, actualDomain.dateCreated)
        assertEquals(testFeed.dateUpdated, actualDomain.dateUpdated)
        assertEquals(testFeed.kicker, actualDomain.kicker)
        assertEquals(testFeed.thumbUrl, actualDomain.thumbUrl)
        assertEquals(testFeed.iconUrl, actualDomain.iconUrl)
        assertEquals(testFeed.iconCaption, actualDomain.iconCaption)
        assertEquals(testFeed.iconCopyright, actualDomain.iconCopyright)
        assertEquals(testFeed.isFavorite, actualDomain.isFavorite)
    }
}
