package com.danil.kleshchin.rss.data.feeds.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity

@Dao
interface FeedDao {

    @Query("SELECT * FROM FEED_TABLE")
    suspend fun getFeedListBySection(): List<FeedDbEntity> //TODO checkout about FLOW

    @Query("DELETE FROM FEED_TABLE")
    suspend fun removeAll()
}
