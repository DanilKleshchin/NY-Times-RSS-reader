package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity.FavoriteFeedEntity

const val VERSION = 1
const val TABLE_NAME = "FAVORITE_FEED_TABLE"

/**
 * Store favorites feeds.
 */
@Database(entities = [FavoriteFeedEntity::class], version = VERSION, exportSchema = false)
abstract class FavoriteFeedDatabase : RoomDatabase() {

    abstract val feedDao: FavoriteFeedDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteFeedDatabase? = null

        fun getInstance(context: Context): FavoriteFeedDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteFeedDatabase::class.java,
                        TABLE_NAME
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
