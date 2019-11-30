package com.dmuharemagic.sparkforreddit.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditNewsResponse(val data: RedditDataResponse)