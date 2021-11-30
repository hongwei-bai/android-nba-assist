package com.hongwei.android_nba_assist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamScheduleDao
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTeamScheduleEntity
import com.hongwei.android_nba_assist.data.room.soccer.SoccerTypeConverters

@Database(
    entities = [
        SoccerTeamScheduleEntity::class
    ], version = 1
)
@TypeConverters(SoccerTypeConverters::class)
abstract class SoccerDatabase : RoomDatabase() {

    abstract fun soccerTeamScheduleDao(): SoccerTeamScheduleDao
}