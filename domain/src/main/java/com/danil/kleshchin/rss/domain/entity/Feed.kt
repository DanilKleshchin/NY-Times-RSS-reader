package com.danil.kleshchin.rss.domain.entity

data class Feed(
    val title: String,
    val description: String,
    val feedPageUrl: String,
    val author: String,
    val dateCreated: String,
    val dateUpdated: String,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String
)
