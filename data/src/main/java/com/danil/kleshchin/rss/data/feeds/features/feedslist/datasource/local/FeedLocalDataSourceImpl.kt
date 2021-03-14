package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity.FeedDbEntity

class FeedLocalDataSourceImpl (
    private val database: FeedDatabase
) : FeedLocalDataSource {

    override suspend fun setFeedList(feedList: List<FeedDbEntity>) {
        database.feedDao.setFeedList(feedList)
    }

    override suspend fun removeFeedBySection(sectionName: String) {
        database.feedDao.removeFeedsBySection(sectionName)
    }

    override suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity> {
        return database.feedDao.getFeedListBySection(sectionName)
    }

    override suspend fun removeAll() {
        database.feedDao.removeAll()
    }
}
