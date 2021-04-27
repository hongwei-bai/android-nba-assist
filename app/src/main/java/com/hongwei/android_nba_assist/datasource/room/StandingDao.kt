package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StandingDao {
    @Query("SELECT * FROM standing WHERE dateDiff = 0")
    fun getStanding(): StandingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(standingEntity: StandingEntity)
}