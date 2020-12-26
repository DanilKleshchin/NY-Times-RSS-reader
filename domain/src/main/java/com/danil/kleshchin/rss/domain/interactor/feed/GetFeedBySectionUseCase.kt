package com.danil.kleshchin.rss.domain.interactor.feed

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.interactor.UseCase
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import com.sun.crypto.provider.Preconditions
import io.reactivex.Observable

class GetFeedBySectionUseCase(
    private val repository: FeedRepository
) : UseCase<List<Feed>, GetFeedBySectionUseCase.Params> {

    override fun execute(params: Params?): Observable<List<Feed>> {
        return repository.getFeedListBySection(params.sectionPosition)
    }

    data class Params(val sectionPosition: Int)
}
