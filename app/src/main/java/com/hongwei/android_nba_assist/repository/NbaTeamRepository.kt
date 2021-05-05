package com.hongwei.android_nba_assist.repository

import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assist.datasource.local.FixEndpoint
import com.hongwei.android_nba_assist.datasource.local.LocalTeamTheme
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamThemeMapper.map
import com.hongwei.android_nba_assist.datasource.model.DataSourceResult
import com.hongwei.android_nba_assist.datasource.model.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.network.service.NbaThemeService
import com.hongwei.android_nba_assist.datasource.room.TeamThemeDao
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.hongwei.android_nba_assist.constant.AppConfigurations.Debug.DEBUG_TEAM_THEME_ON_LOCAL

class NbaTeamRepository @Inject constructor(
    private val nbaThemeService: NbaThemeService,
    private val teamThemeDao: TeamThemeDao,
    private val fixEndpoint: FixEndpoint,
    private val localTeamTheme: LocalTeamTheme
) {
    fun getTeamLogoPlaceholder(team: String): Drawable = fixEndpoint.getDefaultTeamLogo(team)

    fun getTeamBannerPlaceholder(team: String): Drawable = fixEndpoint.getDefaultTeamBanner(team)

    suspend fun fetchTeamThemeFromBackend(team: String) {
        if (DEBUG_TEAM_THEME_ON_LOCAL) {
            val localTheme = localTeamTheme.getTeamTheme(team)
            teamThemeDao.save(localTheme)
        } else {
            val response = nbaThemeService.getTeamTheme(team, -1)
            if (response.isSuccessful && response.body() != null) {
                teamThemeDao.save(response.body()!!.map())
            }
        }
    }

    fun getTeamTheme(team: String): Flow<DataSourceResult<TeamThemeEntity>> {
        return teamThemeDao.getTeamTheme().filter {
            if (it == null) {
                fetchTeamThemeFromBackend(team)
            }
            it != null
        }.map {
            DataSourceSuccessResult(it!!)
        }
    }

    fun debug(): String {
        val sb = StringBuilder()

        val themes = teamThemeDao.getAllRecords()
        themes.forEach {
            sb.append("[${it.team}]${it.bannerUrl}")
        }

        return sb.toString()
    }
}