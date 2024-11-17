package com.mikeapp.sportsmate.data.network.interceptor

import com.mikeapp.sportsmate.AppConfigurations.Network.AUTHORIZATION_HEADER
import com.mikeapp.sportsmate.AppConfigurations.Network.BEARER_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicAccessInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val requestWithAuthorization: Request = originalRequest.newBuilder()
            .header(AUTHORIZATION_HEADER, BEARER_TOKEN)
            .build()
        chain.proceed(requestWithAuthorization)
        return chain.proceed(requestWithAuthorization)
    }
}