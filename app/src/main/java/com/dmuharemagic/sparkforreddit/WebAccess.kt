 package com.dmuharemagic.sparkforreddit

import com.dmuharemagic.sparkforreddit.api.interceptor.RawResponseInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

object WebAccess {
    val api: RedditAPI by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(RawResponseInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://reddit.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return@lazy retrofit.create(RedditAPI::class.java)
    }
}