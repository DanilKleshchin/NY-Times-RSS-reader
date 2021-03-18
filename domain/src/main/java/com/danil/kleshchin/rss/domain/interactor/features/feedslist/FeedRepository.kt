package com.danil.kleshchin.rss.domain.interactor.features.feedslist

import com.danil.kleshchin.rss.domain.entity.Feed

interface FeedRepository {

    suspend fun getFeedListBySection(sectionName: String): ResultWrapper<List<Feed>>
}
