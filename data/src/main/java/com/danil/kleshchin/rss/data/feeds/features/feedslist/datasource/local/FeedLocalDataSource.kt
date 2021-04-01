package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor (
    private val database: FeedDatabase
) {

    suspend fun setFeedList(feedList: List<FeedDbEntity>) {
        database.feedDao.setFeedList(feedList)
    }

    suspend fun removeFeedBySection(sectionName: String) {
        database.feedDao.removeFeedsBySection(sectionName)
    }

    suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity> {
        return database.feedDao.getFeedListBySection(sectionName)
    }

    suspend fun removeAll() {
        database.feedDao.removeAll()
    }
}
