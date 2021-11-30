package com.hami.sports_assist.hilt

import android.content.Context
import com.hami.sports_assist.AppConfigurations
import com.hami.sports_assist.AppConfigurations.Network.HTTP_CONNECT_TIMEOUT
import com.hami.sports_assist.AppConfigurations.Network.HTTP_READ_TIMEOUT
import com.hami.sports_assist.AppConfigurations.Network.HTTP_WRITE_TIMEOUT
import com.hami.sports_assist.data.network.interceptor.PublicAccessInterceptor
import com.hami.sports_assist.data.network.service.NbaStatService
import com.hami.sports_assist.data.network.service.NbaThemeService
import com.hami.sports_assist.data.network.service.SoccerStatService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePublicAccessInterceptor(@ApplicationContext appContext: Context): PublicAccessInterceptor =
        PublicAccessInterceptor(appContext)

    @Provides
    @Singleton
    fun provideOkHttpClient(publicAccessInterceptor: PublicAccessInterceptor): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(publicAccessInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    @Provides
    @Singleton
    fun provideNbaStatService(okHttpClient: OkHttpClient, moshi: Moshi): NbaStatService {
        return Retrofit.Builder()
            .baseUrl(AppConfigurations.Network.NBA_STAT_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(NbaStatService::class.java)
    }

    @Provides
    @Singleton
    fun provideNbaThemeService(okHttpClient: OkHttpClient, moshi: Moshi): NbaThemeService {
        return Retrofit.Builder()
            .baseUrl(AppConfigurations.Network.NBA_THEME_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(NbaThemeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSoccerStatService(okHttpClient: OkHttpClient, moshi: Moshi): SoccerStatService {
        return Retrofit.Builder()
            .baseUrl(AppConfigurations.Network.SOCCER_STAT_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(SoccerStatService::class.java)
    }
}
