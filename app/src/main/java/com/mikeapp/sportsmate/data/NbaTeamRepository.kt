package com.mikeapp.sportsmate.data

import com.mikeapp.sportsmate.AppConfigurations.Network.nbaSeasonStatusEndpoint
import com.mikeapp.sportsmate.AppConfigurations.Network.nbaThemeEndpoint
import com.mikeapp.sportsmate.data.github.GithubApiService
import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.data.mapper.NbaTeamDetailMapper.map
import com.mikeapp.sportsmate.data.network.model.nba.NbaSeasonStatusResponse
import com.mikeapp.sportsmate.data.network.model.nba.NbaTeamDetailResponse
import com.mikeapp.sportsmate.data.room.nba.TeamDetailDao
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    private val githubApiService: GithubApiService,
    private val teamDetailDao: TeamDetailDao,
    private val moshi: Moshi
) {
    suspend fun fetchTeamDetailFromBackend(team: String) {
        withContext(Dispatchers.IO) {
            val response = githubApiService.getFileContent(nbaThemeEndpoint)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val decodedContent = String(
                    android.util.Base64.decode(
                        body.content,
                        android.util.Base64.URL_SAFE
                    )
                )
                val type =
                    Types.newParameterizedType(List::class.java, NbaTeamDetailResponse::class.java)
                val adapter = moshi.adapter<List<NbaTeamDetailResponse>>(type)
                val teamDetailsList: List<NbaTeamDetailResponse> =
                    adapter.fromJson(decodedContent) ?: emptyList()
                val teamDetail = teamDetailsList.firstOrNull { it.team == "gs" }
                teamDetail?.let {
                    teamDetailDao.save(teamDetail.map())
                }
            }
        }
    }

    suspend fun fetchSeasonStatusFromBackend() {
        withContext(Dispatchers.IO) {
            val response = githubApiService.getFileContent(nbaSeasonStatusEndpoint)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val decodedContent = String(
                    android.util.Base64.decode(
                        body.content,
                        android.util.Base64.URL_SAFE
                    )
                )
                val adapter = moshi.adapter(NbaSeasonStatusResponse::class.java)
                val seasonStatus: NbaSeasonStatusResponse? = adapter.fromJson(decodedContent)
                seasonStatus?.let {
                    teamDetailDao.getTeamDetail()?.let { entity ->
                        entity.seasonStatus = seasonStatus.seasonStatus.name
                        teamDetailDao.save(entity)
                    }
                }
            }
        }
    }

    fun getTeamDetailFlow(): Flow<TeamDetailEntity> =
        teamDetailDao.getTeamThemeFlow().onEach { teamDetailEntity ->
            if (teamDetailEntity == null) {
                withContext(Dispatchers.IO) {
                    fetchTeamDetailFromBackend(AppSettings.myNbaTeam)
                    fetchSeasonStatusFromBackend()
                }
            }
        }.filterNotNull()

    fun debug(): String {
        val sb = StringBuilder()

        val themes = teamDetailDao.getAllRecords()
        sb.append("data.size: ${themes.size}")
        themes.forEach {
            sb.append("[${it.team}]${it.bannerUrl}")
        }

        return sb.toString()
    }
}