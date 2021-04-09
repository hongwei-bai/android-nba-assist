package com.hongwei.android_nba_assistant.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TeamScheduleEntity::class], version = 1)
abstract class NbaDatabase : RoomDatabase() {

    abstract fun teamScheduleDao(): TeamScheduleDao
}