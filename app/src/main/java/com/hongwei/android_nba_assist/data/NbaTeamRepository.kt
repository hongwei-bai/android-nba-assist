package com.hongwei.android_nba_assist.data

import com.hongwei.android_nba_assist.constant.AppConfigurations.Debug.DEBUG_TEAM_THEME_ON_LOCAL
import com.hongwei.android_nba_assist.data.local.LocalTeamTheme
import com.hongwei.android_nba_assist.data.mapper.NbaTeamThemeMapper.map
import com.hongwei.android_nba_assist.data.mapper.NbaTeamThemeMapper.mapV1
import com.hongwei.android_nba_assist.data.network.service.NbaThemeService
import com.hongwei.android_nba_assist.data.room.TeamThemeDao
import com.hongwei.android_nba_assist.data.room.TeamThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    private val nbaThemeService: NbaThemeService,
    private val teamThemeDao: TeamThemeDao,
    private val localTeamTheme: LocalTeamTheme
) {


    suspend fun fetchTeamDetailFromBackend(team: String) {
        val response = nbaThemeService.getTeamDetail(team)
        val data = response.body()
        if (response.isSuccessful && data != null) {
            teamThemeDao.save(data.map())
        }
    }

    fun getTeamDetail(team: String): Flow<TeamThemeEntity> {
        return teamThemeDao.getTeamTheme().onEach {
            it ?: fetchTeamDetailFromBackend(team)
        }.filterNotNull()
    }

    @Deprecated("NBA V1 api obsoleted. V2 Use fetchTeamDetail instead.")
    suspend fun fetchTeamThemeFromBackend(team: String) {
        if (DEBUG_TEAM_THEME_ON_LOCAL) {
            val localTheme = localTeamTheme.getTeamTheme(team)
            teamThemeDao.save(localTheme)
        } else {
            val response = nbaThemeService.getTeamTheme(team, -1)
            val data = response.body()
            if (response.isSuccessful && data != null) {
                teamThemeDao.save(data.mapV1())
            }
        }
    }

    @Deprecated("NBA V1 api obsoleted. V2 Use getTeamDetail instead.")
    fun getTeamTheme(team: String): Flow<TeamThemeEntity> {
        return teamThemeDao.getTeamTheme().onEach {
            it ?: fetchTeamThemeFromBackend(team)
        }.filterNotNull()
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