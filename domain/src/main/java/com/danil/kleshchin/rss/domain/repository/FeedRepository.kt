package com.danil.kleshchin.rss.domain.repository

import com.danil.kleshchin.rss.domain.entity.Feed
import io.reactivex.Observable

public interface FeedRepository {

    fun getFeedListBySection(sectionId: Int): Observable<List<Feed>>
}
