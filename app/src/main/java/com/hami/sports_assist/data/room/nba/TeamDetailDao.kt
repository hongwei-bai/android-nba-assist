package com.hami.sports_assist.data.room.nba

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hami.sports_assist.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDetailDao {
    @Query("SELECT * FROM nba_team_detail WHERE apiVersion=$API_VERSION")
    fun getTeamThemeFlow(): Flow<TeamDetailEntity?>

    @Query("SELECT * FROM nba_team_detail WHERE apiVersion=$API_VERSION")
    fun getTeamTheme(): TeamDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(teamThemeEntity: TeamDetailEntity)

    @Query("DELETE FROM nba_team_detail")
    suspend fun clear()

    @Query("SELECT * FROM nba_team_detail")
    fun getAllRecords(): List<TeamDetailEntity>
}