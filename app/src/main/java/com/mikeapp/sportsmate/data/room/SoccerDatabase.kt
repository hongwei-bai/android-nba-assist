package com.mikeapp.sportsmate.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mikeapp.sportsmate.data.room.soccer.SoccerTeamScheduleDao
import com.mikeapp.sportsmate.data.room.soccer.SoccerTeamScheduleEntity
import com.mikeapp.sportsmate.data.room.soccer.SoccerTypeConverters

@Database(
    entities = [
        SoccerTeamScheduleEntity::class
    ], version = 1
)
@TypeConverters(SoccerTypeConverters::class)
abstract class SoccerDatabase : RoomDatabase() {

    abstract fun soccerTeamScheduleDao(): SoccerTeamScheduleDao
}