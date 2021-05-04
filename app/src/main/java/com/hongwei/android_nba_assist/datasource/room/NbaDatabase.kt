package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TeamScheduleEntity::class, StandingEntity::class, TeamThemeEntity::class], version = 1)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun teamScheduleDao(): TeamScheduleDao

    abstract fun standingDao(): StandingDao

    abstract fun teamThemeDao(): TeamThemeDao
}