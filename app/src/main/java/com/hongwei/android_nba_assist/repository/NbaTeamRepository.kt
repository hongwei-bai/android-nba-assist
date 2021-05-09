package com.hongwei.android_nba_assist.repository

import com.hongwei.android_nba_assist.constant.AppConfigurations.Debug.DEBUG_TEAM_THEME_ON_LOCAL
import com.hongwei.android_nba_assist.datasource.local.LocalTeamTheme
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamThemeMapper.map
import com.hongwei.android_nba_assist.datasource.model.DataSourceResult
import com.hongwei.android_nba_assist.datasource.model.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.network.service.NbaThemeService
import com.hongwei.android_nba_assist.datasource.room.TeamThemeDao
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
        private val nbaThemeService: NbaThemeService,
        private val teamThemeDao: TeamThemeDao,
        private val localTeamTheme: LocalTeamTheme
) {
    suspend fun fetchTeamThemeFromBackend(team: String) {
        if (DEBUG_TEAM_THEME_ON_LOCAL) {
            val localTheme = localTeamTheme.getTeamTheme(team)
            teamThemeDao.save(localTheme)
        } else {
            val response = nbaThemeService.getTeamTheme(team, -1)
            val data = response.body()
            if (response.isSuccessful && data != null) {
                teamThemeDao.save(data.map())
            }
        }
    }

    fun getTeamTheme(team: String): Flow<DataSourceResult<TeamThemeEntity>> {
        return teamThemeDao.getTeamTheme().onEach {
            it ?: fetchTeamThemeFromBackend(team)
        }.filterNotNull().map {
            DataSourceSuccessResult(it)
        }
    }

    fun debug(): String {
        val sb = StringBuilder()

        val themes = teamThemeDao.getAllRecords()
        sb.append("data.size: ${themes.size}")
        themes.forEach {
            sb.append("[${it.team}]${it.bannerUrl}")
        }

        return sb.toString()
    }
}