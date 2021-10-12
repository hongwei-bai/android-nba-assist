package com.hongwei.android_nba_assist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamThemeDao {
    @Query("SELECT * FROM team_theme WHERE apiVersion=$API_VERSION")
    fun getTeamTheme(): Flow<TeamThemeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(teamThemeEntity: TeamThemeEntity)

    @Query("DELETE FROM team_theme")
    suspend fun clear()

    @Query("SELECT * FROM team_theme")
    fun getAllRecords(): List<TeamThemeEntity>
}