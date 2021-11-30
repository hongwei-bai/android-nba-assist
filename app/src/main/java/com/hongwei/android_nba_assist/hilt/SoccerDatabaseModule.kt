package com.hongwei.android_nba_assist.hilt

import android.content.Context
import androidx.room.Room
import com.hongwei.android_nba_assist.data.room.SoccerDatabase
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamScheduleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SoccerDatabaseModule {
    @Provides
    fun provideSoccerTeamScheduleDao(soccerDatabase: SoccerDatabase): SoccerTeamScheduleDao {
        return soccerDatabase.soccerTeamScheduleDao()
    }

    @Provides
    @Singleton
    fun provideSoccerDatabase(@ApplicationContext appContext: Context): SoccerDatabase {
        return Room.databaseBuilder(
            appContext,
            SoccerDatabase::class.java,
            "soccer_database"
        ).build()
    }
}


