package com.danil.kleshchin.rss.domain.interactor.feed

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import javax.inject.Inject

class GetFeedBySectionUseCase @Inject constructor(
    private val repository: FeedRepository
) : UseCase<List<Feed>, GetFeedBySectionUseCase.Params> {

    override suspend fun execute(params: Params) =
        repository.getFeedListBySection(params.sectionName)

    data class Params(val sectionName: String)
}
