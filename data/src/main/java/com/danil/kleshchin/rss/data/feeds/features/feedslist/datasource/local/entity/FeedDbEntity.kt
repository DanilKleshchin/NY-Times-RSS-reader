package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.local.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FeedDbEntity(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val sectionName: String,
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
