package com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote

import com.danil.kleshchin.rss.data.feeds.features.feedslist.datasource.remote.entity.FeedObjectApiEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The Top Stories API returns an array of articles currently on the specified section (arts, business, ...).
 *
 * https://api.nytimes.com/svc/topstories/v2/<sectionName>.json?api-key=<yourApiKey]>
 */
interface FeedApi {

    @GET(GET_SECTION_PARAM)
    suspend fun getTopStories(
        @Path(SECTION_NAME_PARAM) sectionName: String
    ): Response<FeedObjectApiEntity>
}
