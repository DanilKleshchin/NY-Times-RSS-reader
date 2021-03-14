package com.danil.kleshchin.rss.entities.feed

import androidx.databinding.ObservableBoolean
import java.io.Serializable

data class FeedEntity(
    val id: Int,
    val title: String,
    val description: String,
    val pageUrl: String,
    val author: String,
    val timeElapsed: String,
    val dateCreated: String,
    val dateUpdated: String,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String,
    val isFavorite: ObservableBoolean
) : Serializable
