package com.mikeapp.sportsmate.data.room.soccer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface SoccerTeamScheduleDao {
    @Query("SELECT * FROM soccer_team_schedule WHERE apiVersion=$API_VERSION")
    fun getSoccerTeamSchedule(): Flow<SoccerTeamScheduleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(soccerTeamScheduleEntity: SoccerTeamScheduleEntity)

    @Query("UPDATE soccer_team_schedule SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM soccer_team_schedule")
    suspend fun clear()

    @Query("SELECT * FROM soccer_team_schedule")
    fun getAllRecords(): List<SoccerTeamScheduleEntity>
}