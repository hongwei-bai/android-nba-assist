package com.mikeapp.sportsmate.data

import com.mikeapp.sportsmate.AppConfigurations.Network.HttpCode
import com.mikeapp.sportsmate.AppConfigurations.Network.nbaTeamScheduleEndpoint
import com.mikeapp.sportsmate.data.github.GithubApiService
import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.data.mapper.NbaPostSeasonMapper.map
import com.mikeapp.sportsmate.data.mapper.NbaStandingMapper.map
import com.mikeapp.sportsmate.data.mapper.NbaTeamScheduleMapper.map
import com.mikeapp.sportsmate.data.mapper.NbaTransactionsMapper.map
import com.mikeapp.sportsmate.data.network.model.nba.TeamScheduleResponse
import com.mikeapp.sportsmate.data.network.service.NbaStatService
import com.mikeapp.sportsmate.data.room.nba.NbaTransactionsDao
import com.mikeapp.sportsmate.data.room.nba.NbaTransactionsEntity
import com.mikeapp.sportsmate.data.room.nba.PostSeasonDao
import com.mikeapp.sportsmate.data.room.nba.PostSeasonEntity
import com.mikeapp.sportsmate.data.room.nba.StandingDao
import com.mikeapp.sportsmate.data.room.nba.StandingEntity
import com.mikeapp.sportsmate.data.room.nba.TeamDetailDao
import com.mikeapp.sportsmate.data.room.nba.TeamEvent
import com.mikeapp.sportsmate.data.room.nba.TeamScheduleDao
import com.mikeapp.sportsmate.data.util.DataValidationUtil.after
import com.mikeapp.sportsmate.data.util.DataValidationUtil.dataMayOutdated
import com.mikeapp.sportsmate.ui.component.DataStatus
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getFirstDayOfWeek
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val githubApiService: GithubApiService,
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val teamDetailDao: TeamDetailDao,
    private val standingDao: StandingDao,
    private val postSeasonDao: PostSeasonDao,
    private val nbaTransactionsDao: NbaTransactionsDao,
    private val moshi: Moshi
) {
    private val dataStatusChannel = Channel<DataStatus>()

    val dataStatus: Flow<DataStatus> = dataStatusChannel.receiveAsFlow()

    fun getNextGameInfo(team: String): Flow<TeamEvent> {
        return teamScheduleDao.getTeamSchedule().onEach {
            it ?: fetchTeamScheduleFromBackend(team)
        }.filterNotNull().map {
            it.events.firstOrNull { event ->
                after(event.unixTimeStamp)
            }
        }.filterNotNull()
    }

    fun getTeamSchedule(team: String): Flow<List<TeamEvent>> {
        return teamScheduleDao.getTeamSchedule().onEach {
            when {
                it == null -> fetchTeamScheduleFromBackend(team)
                dataMayOutdated(it.timeStamp) -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchTeamScheduleFromBackend(team, it.sha)
                }
            }
        }.filterNotNull().map {
            it.events.filter { eventTime ->
                eventTime.unixTimeStamp > getFirstDayOfWeek(AppSettings.weekStartsFromMonday).timeInMillis
            }
        }
    }

    fun getStanding(): Flow<StandingEntity> {
        return standingDao.getStanding().onEach {
            when {
                it == null -> fetchStandingFromBackend()
                dataMayOutdated(it.timeStamp) -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchStandingFromBackend(it.dataVersion)
                }
            }
        }.filterNotNull()
    }

    fun getPostSeason(): Flow<PostSeasonEntity> {
        return postSeasonDao.getPostSeason().onEach {
            when {
                it == null -> fetchPostSeasonFromBackend()
                dataMayOutdated(it.timeStamp) -> {
                    dataStatusChannel.send(DataStatus.DataMayOutdated)
                    fetchPostSeasonFromBackend(it.dataVersion)
                }
            }
        }.filterNotNull()
    }

    fun getTransactions(): Flow<NbaTransactionsEntity> {
        return nbaTransactionsDao.getTransactions().onEach {
            it ?: fetchTransactionsFromBackend()
        }.filterNotNull()
    }

    suspend fun fetchTeamScheduleFromBackend(team: String, sha: String? = null) {
        withContext(Dispatchers.IO) {
            val responseMetadata = githubApiService.getFileMetadata(nbaTeamScheduleEndpoint(team))
            val responseSha = if (responseMetadata.isSuccessful) {
                responseMetadata.body()?.sha
            } else null
            if (sha != null && sha == responseSha) {
                dataStatusChannel.send(DataStatus.DataIsUpToDate)
                teamScheduleDao.update(System.currentTimeMillis())
            } else {
                val response = githubApiService.getFileContent(nbaTeamScheduleEndpoint(team))
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    val decodedContent = String(
                        android.util.Base64.decode(
                            body.content,
                            android.util.Base64.URL_SAFE
                        )
                    )
                    val adapter = moshi.adapter(TeamScheduleResponse::class.java)
                    val teamSchedule = adapter.fromJson(decodedContent)
                    teamSchedule?.let {
                        val teamDetail = teamDetailDao.getTeamTheme()
                        teamDetail?.let {
                            teamScheduleDao.save(teamSchedule.map(teamDetail.team))
                        }
                    }
                } else {
                    dataStatusChannel.send(
                        DataStatus.ServiceError("Fetch schedules data error, code: ${response.code()}")
                    )
                }
            }
        }
    }

    suspend fun fetchStandingFromBackend(dataVersion: Long? = null) {
        withContext(Dispatchers.IO) {
            val response = nbaStatService.getStanding(dataVersion ?: -1)
            val data = response.body()
            when (response.code()) {
                HttpCode.HTTP_OK -> data?.let { standingDao.save(data.map()) }
                HttpCode.HTTP_DATA_UP_TO_DATE -> {
                    dataStatusChannel.send(DataStatus.DataIsUpToDate)
                    standingDao.update(System.currentTimeMillis())
                }

                else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch standings data error, code: ${response.code()}"))
            }
        }
    }

    suspend fun fetchPostSeasonFromBackend(dataVersion: Long? = null) {
        withContext(Dispatchers.IO) {
            val response = nbaStatService.getPostSeason(dataVersion ?: -1)
            val data = response.body()
            when (response.code()) {
                HttpCode.HTTP_OK -> data?.let {
                    postSeasonDao.save(data.map())
                }

                HttpCode.HTTP_DATA_UP_TO_DATE -> {
                    dataStatusChannel.send(DataStatus.DataIsUpToDate)
                    postSeasonDao.update(System.currentTimeMillis())
                }

                else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch Playoff data error, code: ${response.code()}"))
            }
        }
    }

    suspend fun fetchTransactionsFromBackend(dataVersion: Long? = null) {
        withContext(Dispatchers.IO) {
            val response = nbaStatService.getTransactions(dataVersion ?: -1)
            val data = response.body()
            when (response.code()) {
                HttpCode.HTTP_OK -> data?.let {
                    nbaTransactionsDao.save(data.map())
                }

                HttpCode.HTTP_DATA_UP_TO_DATE -> {
                    dataStatusChannel.send(DataStatus.DataIsUpToDate)
                }

                else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch transactions data error, code: ${response.code()}"))
            }
        }
    }
}