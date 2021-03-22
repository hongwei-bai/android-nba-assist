package com.hongwei.android_nba_assistant.di.module

import com.hongwei.android_nba_assistant.constant.AppConfigurations
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.HTTP_CONNECT_TIMEOUT
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.HTTP_READ_TIMEOUT
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Network.HTTP_WRITE_TIMEOUT
import com.hongwei.android_nba_assistant.datasource.network.service.NbaStatService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    @Provides
    @Singleton
    fun provideNbaStatService(okHttpClient: OkHttpClient): NbaStatService {
        return Retrofit.Builder()
            .baseUrl(AppConfigurations.Network.NBA_STAT_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NbaStatService::class.java)
    }
}
