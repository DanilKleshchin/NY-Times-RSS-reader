package com.danil.kleshchin.rss.domain.interactor.features.feedslist.usecases

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.FeedRepository
import com.danil.kleshchin.rss.domain.interactor.features.feedslist.ResultWrapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedListBySectionUseCase @Inject constructor(
    private val repository: FeedRepository
) : UseCase<Flow<ResultWrapper<List<Feed>>>, GetFeedListBySectionUseCase.Params> {

    override suspend fun execute(params: Params) =
        repository.getFeedListBySection(params.sectionName)

    data class Params(val sectionName: String)
}
