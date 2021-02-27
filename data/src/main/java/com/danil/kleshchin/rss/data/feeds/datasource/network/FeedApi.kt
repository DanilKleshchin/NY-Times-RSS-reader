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

    @GET("{section_name}.json")
    suspend fun getTopStories(
        @Path("section_name") sectionName: String,
        @Query("api-key") apiKey: String
    ): FeedObjectApiEntity
}
