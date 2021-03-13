package com.danil.kleshchin.rss.data.feeds.datasource.local

import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity

interface FeedLocalDataSource {

    suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity>

    suspend fun removeAll()
}