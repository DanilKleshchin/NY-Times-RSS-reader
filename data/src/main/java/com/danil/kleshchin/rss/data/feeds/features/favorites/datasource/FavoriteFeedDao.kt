package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

@Dao
interface FavoriteFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeed(feed: FavoriteFeedEntity)

    @Query("DELETE FROM FAVORITE_FEED_TABLE WHERE toRemove = :toRemove")
    suspend fun removeFeeds(toRemove: Boolean = true)

    @Query("SELECT * FROM FAVORITE_FEED_TABLE WHERE toRemove = :toRemove ORDER BY position DESC")
    suspend fun getFeedList(toRemove: Boolean = false): List<FavoriteFeedEntity>

    @Query("UPDATE FAVORITE_FEED_TABLE SET toRemove = :toRemove WHERE id = :id")
    suspend fun markFeedToRemove(id: Int, toRemove: Boolean)
}
