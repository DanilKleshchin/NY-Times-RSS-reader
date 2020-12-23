package com.danil.kleshchin.rss.domain.entity

data class Feed(
    val title: String,
    val description: String,
    val fulFeedPageUrl: String,
    val date: String,
    val iconUrl: String
)
