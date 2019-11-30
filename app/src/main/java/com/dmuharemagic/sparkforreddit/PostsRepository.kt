package com.dmuharemagic.sparkforreddit

import android.app.Application
import androidx.lifecycle.LiveData
import com.dmuharemagic.sparkforreddit.model.RedditNews
import com.dmuharemagic.sparkforreddit.model.RedditNewsDataResponse
import com.dmuharemagic.sparkforreddit.model.RedditNewsResponse
import kotlinx.coroutines.Deferred

class PostsRepository(application: Application) {

    private var db: AppDatabase = AppDatabase.getInstance(application)

    suspend fun getPostsWithCache(after: String, limit: String = "10"): LiveData<Resource<RedditNews>> {
        println("Getting posts from repo - after = $after and limit = $limit")
        return object: NetworkBoundResource<RedditNews, RedditNewsResponse>() {
            override fun processResponse(response: RedditNewsResponse): RedditNews {
                val listing: ArrayList<RedditNewsDataResponse> = ArrayList()

                response.data.children.forEach {
                    listing.add(it.data)
                }

                return RedditNews(response.data.after.orEmpty(), response.data.before.orEmpty(), listing)
            }

            override suspend fun saveCallResults(items: RedditNews) = db.postDao().insertPosts(items)

            override fun shouldFetch(data: RedditNews?): Boolean = true

            override suspend fun loadFromDb(): RedditNews = db.postDao().getPosts()

            override fun createCallAsync(): Deferred<RedditNewsResponse> = WebAccess.api.getMain(after, limit)
        }.build().asLiveData()
    }


}