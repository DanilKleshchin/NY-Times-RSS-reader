package com.danil.kleshchin.rss.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.danil.kleshchin.rss.data.db.utils.DB_POSITION
import com.danil.kleshchin.rss.data.db.utils.SECTION_NAME
import com.danil.kleshchin.rss.data.db.utils.testFeed
import com.danil.kleshchin.rss.data.db.utils.testFeedDbEntity
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDao
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.FeedDatabase
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FeedLocalDatabaseTest {

    private lateinit var feedDbEntityList: ArrayList<FeedDbEntity>
    private lateinit var actualFeedDbEntity: FeedDbEntity
    private lateinit var feedDao: FeedDao
    private lateinit var db: FeedDatabase

    @Before
    fun createDatabase() {
        feedDbEntityList = ArrayList()
        feedDbEntityList.add(testFeedDbEntity)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, FeedDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        feedDao = db.feedDao
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTvShowPopularDbEntity() {
        runBlocking {
            feedDao.setFeedList(feedDbEntityList)
            actualFeedDbEntity = feedDao.getFeedListBySection(SECTION_NAME)[0]

            Assert.assertEquals(testFeed.id, actualFeedDbEntity.id)
            Assert.assertEquals(DB_POSITION, actualFeedDbEntity.position)
            Assert.assertEquals(SECTION_NAME, actualFeedDbEntity.sectionName)
            Assert.assertEquals(testFeed.title, actualFeedDbEntity.title)
            Assert.assertEquals(testFeed.description, actualFeedDbEntity.description)
            Assert.assertEquals(testFeed.pageUrl, actualFeedDbEntity.pageUrl)
            Assert.assertEquals(testFeed.author, actualFeedDbEntity.author)
            Assert.assertEquals(testFeed.dateCreated, actualFeedDbEntity.dateCreated)
            Assert.assertEquals(testFeed.dateUpdated, actualFeedDbEntity.dateUpdated)
            Assert.assertEquals(testFeed.kicker, actualFeedDbEntity.kicker)
            Assert.assertEquals(testFeed.thumbUrl, actualFeedDbEntity.thumbUrl)
            Assert.assertEquals(testFeed.iconUrl, actualFeedDbEntity.iconUrl)
            Assert.assertEquals(testFeed.iconCaption, actualFeedDbEntity.iconCaption)
            Assert.assertEquals(testFeed.iconCopyright, actualFeedDbEntity.iconCopyright)
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}
