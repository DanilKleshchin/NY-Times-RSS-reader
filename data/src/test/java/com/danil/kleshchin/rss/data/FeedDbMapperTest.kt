package com.danil.kleshchin.rss.data

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDbMapper
import com.danil.kleshchin.rss.data.utils.DB_POSITION
import com.danil.kleshchin.rss.data.utils.SECTION_NAME
import com.danil.kleshchin.rss.data.utils.testFeed
import com.danil.kleshchin.rss.data.utils.testFeedDbEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FeedDbMapperTest {

    private lateinit var mapper: FeedDbMapper

    @Before
    fun setUp() {
        mapper = FeedDbMapper()
    }

    @Test
    fun transform_to_domain_test() {
        val actualDomain = mapper.transform(testFeedDbEntity)

        Assert.assertEquals(testFeed.title, actualDomain.title)
        Assert.assertEquals(testFeed.description, actualDomain.description)
        Assert.assertEquals(testFeed.pageUrl, actualDomain.pageUrl)
        Assert.assertEquals(testFeed.author, actualDomain.author)
        Assert.assertEquals(testFeed.dateCreated, actualDomain.dateCreated)
        Assert.assertEquals(testFeed.dateUpdated, actualDomain.dateUpdated)
        Assert.assertEquals(testFeed.kicker, actualDomain.kicker)
        Assert.assertEquals(testFeed.thumbUrl, actualDomain.thumbUrl)
        Assert.assertEquals(testFeed.iconUrl, actualDomain.iconUrl)
        Assert.assertEquals(testFeed.iconCaption, actualDomain.iconCaption)
        Assert.assertEquals(testFeed.iconCopyright, actualDomain.iconCopyright)
        Assert.assertEquals(testFeed.isFavorite, actualDomain.isFavorite)
    }

    @Test
    fun transform_to_db_test() {
        val actualDomain = mapper.transform(SECTION_NAME, DB_POSITION, testFeed)

        Assert.assertEquals(testFeed.id, actualDomain.id)
        Assert.assertEquals(DB_POSITION, actualDomain.position)
        Assert.assertEquals(SECTION_NAME, actualDomain.sectionName)
        Assert.assertEquals(testFeed.title, actualDomain.title)
        Assert.assertEquals(testFeed.description, actualDomain.description)
        Assert.assertEquals(testFeed.pageUrl, actualDomain.pageUrl)
        Assert.assertEquals(testFeed.author, actualDomain.author)
        Assert.assertEquals(testFeed.dateCreated, actualDomain.dateCreated)
        Assert.assertEquals(testFeed.dateUpdated, actualDomain.dateUpdated)
        Assert.assertEquals(testFeed.kicker, actualDomain.kicker)
        Assert.assertEquals(testFeed.thumbUrl, actualDomain.thumbUrl)
        Assert.assertEquals(testFeed.iconUrl, actualDomain.iconUrl)
        Assert.assertEquals(testFeed.iconCaption, actualDomain.iconCaption)
        Assert.assertEquals(testFeed.iconCopyright, actualDomain.iconCopyright)
    }
}
