package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

@Dao
interface FavoriteFeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeedToFavorites(feed: FavoriteFeedEntity)

    @Query("DELETE FROM FAVORITE_FEED_TABLE WHERE id = :feedId")
    suspend fun removeFeedFromFavorites(feedId: Int)

    @Query("SELECT * FROM FAVORITE_FEED_TABLE")
    suspend fun getFavoritesFeedList(): List<FavoriteFeedEntity>
}
