package com.danil.kleshchin.rss.data.feeds.datasource.network

import com.danil.kleshchin.rss.data.feeds.entity.FeedObjectApiEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The Top Stories API returns an array of articles currently on the specified section (arts, business, ...).
 *
 * https://api.nytimes.com/svc/topstories/v2/<sectionName>.json?api-key=<yourApiKey]>
 */
interface FeedApi {

    @GET(GET_SECTION_PARAM)
    suspend fun getTopStories(
        @Path(SECTION_NAME_PARAM) sectionName: String,
        @Query(API_KEY_PARAM) apiKey: String
    ): FeedObjectApiEntity
}
