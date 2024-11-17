package com.mikeapp.sportsmate.di

import com.mikeapp.sportsmate.AppConfigurations.Network.HTTP_CONNECT_TIMEOUT
import com.mikeapp.sportsmate.AppConfigurations.Network.HTTP_READ_TIMEOUT
import com.mikeapp.sportsmate.AppConfigurations.Network.HTTP_WRITE_TIMEOUT
import com.mikeapp.sportsmate.AppConfigurations.Network.SPORTS_HUB_GITHUB_API_BASE
import com.mikeapp.sportsmate.data.github.GithubApiService
import com.mikeapp.sportsmate.data.network.interceptor.PublicAccessInterceptor
import com.mikeapp.sportsmate.data.network.service.NbaStatService
import com.mikeapp.sportsmate.data.network.service.NbaThemeService
import com.mikeapp.sportsmate.data.network.service.SoccerStatService
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
    fun providePublicAccessInterceptor(): PublicAccessInterceptor = PublicAccessInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(publicAccessInterceptor: PublicAccessInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(publicAccessInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SPORTS_HUB_GITHUB_API_BASE)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApiService(retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNbaStatService(retrofit: Retrofit): NbaStatService {
        return retrofit.create(NbaStatService::class.java)
    }

    @Provides
    @Singleton
    fun provideNbaThemeService(retrofit: Retrofit): NbaThemeService {
        return retrofit.create(NbaThemeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSoccerStatService(retrofit: Retrofit): SoccerStatService {
        return retrofit.create(SoccerStatService::class.java)
    }
}
