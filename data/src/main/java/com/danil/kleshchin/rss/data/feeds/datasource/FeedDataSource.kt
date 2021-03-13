package com.danil.kleshchin.rss.data.feeds.datasource

import com.danil.kleshchin.rss.data.feeds.datasource.network.entity.FeedObjectApiEntity

interface FeedDataSource {

    suspend fun getTopStoriesBySection(section: String, apiKey: String): FeedObjectApiEntity
}
