package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeamScheduleDao {
    @Query("SELECT * FROM team_schedule WHERE team = :team")
    fun getTeamSchedule(team: String): TeamScheduleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teamScheduleEntity: TeamScheduleEntity)
}