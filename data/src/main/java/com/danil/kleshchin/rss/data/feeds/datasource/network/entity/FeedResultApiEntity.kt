package com.danil.kleshchin.rss.data.feeds.datasource.network.entity

import com.google.gson.annotations.SerializedName

data class FeedResultApiEntity(
    val title: String,
    val abstract: String,
    val url: String,
    val byline: String,
    val kicker: String?,
    val multimedia: List<FeedMultimediaApiEntity>?,

    @SerializedName("created_date")
    val dateCreated: String,

    @SerializedName("updated_date")
    val dateUpdated: String

)
