package com.danil.kleshchin.rss.domain.interactor.features.feedslist

import com.danil.kleshchin.rss.domain.entity.Feed
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    suspend fun getFeedListBySection(sectionName: String): Flow<Result<List<Feed>>>
}
