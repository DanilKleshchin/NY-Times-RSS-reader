package com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danil.kleshchin.rss.data.feeds.features.favorites.datasource.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteFeedEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val pageUrl: String,
    val author: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String,
)
