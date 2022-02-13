package com.hami.sports_assist.data.network.interceptor

import com.hami.sports_assist.AppConfigurations.Network.AUTHORIZATION_BEARER
import com.hami.sports_assist.AppConfigurations.Network.AUTHORIZATION_HEADER
import com.hami.sports_assist.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicAccessInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = BuildConfig.publicAccessToken
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $jwt")
            .build()
        return chain.proceed(newRequest)
    }
}