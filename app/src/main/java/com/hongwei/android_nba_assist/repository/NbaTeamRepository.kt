package com.hongwei.android_nba_assist.repository

import android.content.Context
import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assist.datasource.mapper.NbaTeamThemeMapper.map
import com.hongwei.android_nba_assist.datasource.model.DataSourceResult
import com.hongwei.android_nba_assist.datasource.model.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.network.service.NbaThemeService
import com.hongwei.android_nba_assist.datasource.room.TeamThemeDao
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.util.ResourceByNameUtil.getDrawableByName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val nbaThemeService: NbaThemeService,
    private val teamThemeDao: TeamThemeDao
) {
    fun getTeamLogoPlaceholder(team: String): Drawable = getDrawableByName(context, team)

    suspend fun fetchTeamThemeFromBackend(team: String) {
        val response = nbaThemeService.getTeamTheme(team, -1)
        if (response.isSuccessful && response.body() != null) {
            teamThemeDao.save(response.body()!!.map())
        }
    }

    fun getTeamTheme(team: String): Flow<DataSourceResult<TeamThemeEntity>> {
        return teamThemeDao.getTeamTheme(team).filter {
            if (it == null) {
                fetchTeamThemeFromBackend(team)
            }
            it != null
        }.map {
            DataSourceSuccessResult(it!!)
        }
    }
}