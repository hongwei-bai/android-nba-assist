package com.mikeapp.sportsmate.data.network.interceptor

import com.mikeapp.sportsmate.AppConfigurations.Network.AUTHORIZATION_BEARER
import com.mikeapp.sportsmate.AppConfigurations.Network.AUTHORIZATION_HEADER
import com.mikeapp.sportsmate.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicAccessInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = BuildConfig.STATIC_API_TOKEN
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $jwt")
            .build()
        return chain.proceed(newRequest)
    }
}