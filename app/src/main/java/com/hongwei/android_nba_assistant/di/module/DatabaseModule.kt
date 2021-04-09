package com.hongwei.android_nba_assistant.di.module

import android.content.Context
import androidx.room.Room
import com.hongwei.android_nba_assistant.datasource.room.NbaDatabase
import com.hongwei.android_nba_assistant.datasource.room.TeamScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(nbaDatabase: NbaDatabase): TeamScheduleDao {
        return nbaDatabase.teamScheduleDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NbaDatabase {
        return Room.databaseBuilder(
            appContext,
            NbaDatabase::class.java,
            "nba_database"
        ).build()
    }
}


