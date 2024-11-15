package com.mikeapp.sportsmate.data.room.nba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface PostSeasonDao {
    @Query("SELECT * FROM nba_post_season WHERE apiVersion=$API_VERSION")
    fun getPostSeason(): Flow<PostSeasonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(postSeasonEntity: PostSeasonEntity)

    @Query("UPDATE nba_post_season SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM nba_post_season")
    suspend fun clear()

    @Query("SELECT * FROM nba_post_season")
    fun getAllRecords(): List<PostSeasonEntity>
}