package com.danil.kleshchin.rss.data.feeds.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danil.kleshchin.rss.data.feeds.datasource.local.entity.FeedDbEntity

const val VERSION = 1
const val TABLE_NAME = "FEED_TABLE"

/**
 * Store top stories feeds.
 */
@Database(entities = [FeedDbEntity::class], version = VERSION, exportSchema = false)
abstract class FeedDatabase : RoomDatabase() {

    abstract val feedDao: FeedDao

    companion object {
        @Volatile
        private var INSTANCE: FeedDatabase? = null

        fun getInstance(context: Context): FeedDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FeedDatabase::class.java,
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
