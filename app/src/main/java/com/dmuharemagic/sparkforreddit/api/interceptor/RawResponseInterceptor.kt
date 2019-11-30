package com.dmuharemagic.sparkforreddit.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class RawResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url().newBuilder()
            .addQueryParameter("raw_json", "1").build()
        val newRequest = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
