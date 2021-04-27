package com.hongwei.android_nba_assist.datasource.network.interceptor

import android.content.Context
import com.hongwei.android_nba_assist.R
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.AUTHORIZATION_BEARER
import com.hongwei.android_nba_assist.constant.AppConfigurations.Network.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicAccessInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = context.getString(R.string.public_access_token);
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $jwt")
            .build()
        return chain.proceed(newRequest)
    }
}