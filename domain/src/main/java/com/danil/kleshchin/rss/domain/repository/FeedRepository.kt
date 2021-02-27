package com.danil.kleshchin.rss.domain.repository

import com.danil.kleshchin.rss.domain.entity.Feed

interface FeedRepository {

    suspend fun getFeedListBySection(sectionName: String): List<Feed>
}
