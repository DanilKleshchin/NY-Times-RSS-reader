package com.danil.kleshchin.rss.domain.entity

data class Feed(
    val title: String,
    val description: String,
    val feedPageUrl: String,
    val author: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val kicker: String,
    val thumbUrl: String,
    val iconUrl: String,
    val iconCaption: String,
    val iconCopyright: String
)
