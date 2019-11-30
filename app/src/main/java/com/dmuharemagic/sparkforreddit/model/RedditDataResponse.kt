package com.dmuharemagic.sparkforreddit.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditDataResponse(
    val children: List<RedditChildrenResponse>,
    val after: String?,
    val before: String?
)
