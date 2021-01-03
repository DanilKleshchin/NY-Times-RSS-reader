package com.danil.kleshchin.rss.data.feeds

import com.danil.kleshchin.rss.domain.entity.Feed
import com.danil.kleshchin.rss.domain.repository.FeedRepository
import io.reactivex.Observable
import javax.inject.Inject

class FeedDataRepository : FeedRepository {

    override fun getFeedListBySection(sectionName: String): Observable<List<Feed>> {
        val feeds = listOf(Feed("Stub title", "description", "url", "date", "icon"))
        return Observable.fromArray(feeds)
    }
}
