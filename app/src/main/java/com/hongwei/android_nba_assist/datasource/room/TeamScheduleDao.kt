package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamScheduleDao {
    @Query("SELECT * FROM team_schedule WHERE apiVersion=$API_VERSION")
    fun getTeamSchedule(): Flow<TeamScheduleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(teamScheduleEntity: TeamScheduleEntity)

    @Query("DELETE FROM team_schedule")
    suspend fun clear()

    @Query("SELECT * FROM team_schedule")
    fun getAllRecords(): List<TeamScheduleEntity>
}