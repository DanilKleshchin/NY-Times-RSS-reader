package com.danil.kleshchin.rss.entities.feed

import java.io.Serializable

data class FeedEntity(
    val title: String,
    val description: String,
    val feedPageUrl: String,
    val author: String,
    val timeElapsed: String,
    val dateCreated: String,
    val dateUpdated: String,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String
) : Serializable
