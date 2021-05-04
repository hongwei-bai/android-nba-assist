package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamThemeDao {
    @Query("SELECT * FROM team_theme WHERE team = :team")
    fun getTeamTheme(team: String): Flow<TeamThemeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(teamThemeEntity: TeamThemeEntity)
}