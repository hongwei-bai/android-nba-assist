package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayOffDao {
    @Query("SELECT * FROM playoff WHERE apiVersion=$API_VERSION")
    fun getStanding(): Flow<PlayOffEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(playOffEntity: PlayOffEntity)

    @Query("UPDATE playoff SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM playoff")
    suspend fun clear()

    @Query("SELECT * FROM playoff")
    fun getAllRecords(): List<PlayOffEntity>
}