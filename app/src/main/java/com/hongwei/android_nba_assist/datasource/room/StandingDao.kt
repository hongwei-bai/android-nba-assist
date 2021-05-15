package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface StandingDao {
    @Query("SELECT * FROM standing WHERE apiVersion=$API_VERSION")
    fun getStanding(): Flow<StandingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(standingEntity: StandingEntity)

    @Query("UPDATE standing SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM standing")
    suspend fun clear()

    @Query("SELECT * FROM standing")
    fun getAllRecords(): List<StandingEntity>
}