package com.danil.kleshchin.rss.data.feeds.datasource.local

import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity

class FeedLocalDataSourceImpl (
    private val database: FeedDatabase
) : FeedLocalDataSource {

    override suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity> {
        return database.feedDao.getFeedListBySection()
    }

    override suspend fun removeAll() {
        database.feedDao.removeAll()
    }
}
