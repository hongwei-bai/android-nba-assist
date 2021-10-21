package com.hongwei.android_nba_assist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface StandingDao {
    @Query("SELECT * FROM nba_standing WHERE apiVersion=$API_VERSION")
    fun getStanding(): Flow<StandingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(standingEntity: StandingEntity)

    @Query("UPDATE nba_standing SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM nba_standing")
    suspend fun clear()

    @Query("SELECT * FROM nba_standing")
    fun getAllRecords(): List<StandingEntity>
}