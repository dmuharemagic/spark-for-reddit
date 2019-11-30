package com.dmuharemagic.sparkforreddit;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dmuharemagic.sparkforreddit.model.RedditNews

@Dao
interface PostDao {
    @Query("SELECT * from posts")
    suspend fun getPosts(): RedditNews

    @Insert(onConflict = REPLACE)
    fun insertPosts(posts: RedditNews)

    @Query("DELETE FROM posts")
    fun deletePosts()
}
