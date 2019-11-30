package com.dmuharemagic.sparkforreddit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType

@Entity(tableName = "posts", primaryKeys = arrayOf("after", "limit"))
data class RedditNews(
                      val after: String,
                      val limit: String,
                      @TypeConverters(NestedDataTypeConverter::class)
                      var data: List<RedditNewsDataResponse>? = null)

class NestedDataTypeConverter {

    val moshi = Moshi.Builder().build()
    var listMyData = newParameterizedType(List::class.java, RedditNewsDataResponse::class.java)
    var adapter = moshi.adapter<List<RedditNewsDataResponse>>(listMyData)

    /**
     * Convert a a list of Images to a Json
     */
    @TypeConverter
    fun fromImagesJson(list: List<RedditNewsDataResponse>): String {
        return adapter.toJson(list)
    }

    /**
     * Convert a json to a list of Images
     */
    @TypeConverter
    fun toImagesList(json: String): List<RedditNewsDataResponse>? {
        return adapter.fromJson(json);
    }
}