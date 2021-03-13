package com.danil.kleshchin.rss.data.feeds.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity

@Dao
interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFeedList(list: List<FeedDbEntity>)

    @Query("DELETE FROM FEED_TABLE WHERE sectionName = :sectionName")
    suspend fun removeFeedsBySection(sectionName: String)

    @Query("SELECT * FROM FEED_TABLE WHERE sectionName = :sectionName")
    suspend fun getFeedListBySection(sectionName: String): List<FeedDbEntity> //TODO checkout about FLOW

    @Query("DELETE FROM FEED_TABLE")
    suspend fun removeAll()
}
