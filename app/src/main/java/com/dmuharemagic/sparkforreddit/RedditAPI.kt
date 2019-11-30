package com.dmuharemagic.sparkforreddit

import com.dmuharemagic.sparkforreddit.model.RedditNewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditAPI {
    @GET("r/IAmA.json")
    fun getMain(@Query("after") after: String,
                        @Query("limit") limit: String): Deferred<RedditNewsResponse>
}
