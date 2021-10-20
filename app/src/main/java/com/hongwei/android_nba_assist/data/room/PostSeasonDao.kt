package com.hongwei.android_nba_assist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface PostSeasonDao {
    @Query("SELECT * FROM post_season WHERE apiVersion=$API_VERSION")
    fun getPostSeason(): Flow<PostSeasonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(postSeasonEntity: PostSeasonEntity)

    @Query("UPDATE post_season SET timeStamp = :timeStamp WHERE apiVersion=$API_VERSION")
    suspend fun update(timeStamp: Long)

    @Query("DELETE FROM post_season")
    suspend fun clear()

    @Query("SELECT * FROM post_season")
    fun getAllRecords(): List<PostSeasonEntity>
}