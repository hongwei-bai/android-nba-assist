package com.hongwei.android_nba_assist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        TeamScheduleEntity::class,
        StandingEntity::class,
        PostSeasonEntity::class,
        TeamDetailEntity::class,
        NbaTransactionsEntity::class
    ], version = 1
)
@TypeConverters(NbaTypeConverters::class)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun teamScheduleDao(): TeamScheduleDao

    abstract fun standingDao(): StandingDao

    abstract fun playOff(): PostSeasonDao

    abstract fun teamThemeDao(): TeamDetailDao

    abstract fun transactionsDao(): NbaTransactionsDao
}