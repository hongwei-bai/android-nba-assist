package com.hongwei.android_nba_assist.hilt

import android.content.Context
import androidx.room.Room
import com.hongwei.android_nba_assist.data.room.NbaDatabase
import com.hongwei.android_nba_assist.data.room.nba.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NbaDatabaseModule {
    @Provides
    fun provideTeamScheduleDao(nbaDatabase: NbaDatabase): TeamScheduleDao {
        return nbaDatabase.teamScheduleDao()
    }

    @Provides
    fun provideStandingDao(nbaDatabase: NbaDatabase): StandingDao {
        return nbaDatabase.standingDao()
    }

    @Provides
    fun providePlayOff(nbaDatabase: NbaDatabase): PostSeasonDao {
        return nbaDatabase.playOff()
    }

    @Provides
    fun provideTeamThemeDao(nbaDatabase: NbaDatabase): TeamDetailDao {
        return nbaDatabase.teamThemeDao()
    }

    @Provides
    fun provideTransactionsDao(nbaDatabase: NbaDatabase): NbaTransactionsDao {
        return nbaDatabase.transactionsDao()
    }

    @Provides
    @Singleton
    fun provideNbaDatabase(@ApplicationContext appContext: Context): NbaDatabase {
        return Room.databaseBuilder(
            appContext,
            NbaDatabase::class.java,
            "nba_database"
        ).build()
    }
}


