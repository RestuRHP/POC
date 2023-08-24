package com.example.pocvideo.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private val staticToken = "1|uuzGwwg4gnK2ouno3eq4vlcVPfLHJ21w6cZuR24l"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $staticToken").build()
        return chain.proceed(request.build())
    }
}