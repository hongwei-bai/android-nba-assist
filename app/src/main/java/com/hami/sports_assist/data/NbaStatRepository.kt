package com.hami.sports_assist.data

import com.hami.sports_assist.AppConfigurations.Network.HttpCode
import com.hami.sports_assist.data.local.AppSettings
import com.hami.sports_assist.data.mapper.NbaPostSeasonMapper.map
import com.hami.sports_assist.data.mapper.NbaStandingMapper.map
import com.hami.sports_assist.data.mapper.NbaTeamScheduleMapper.map
import com.hami.sports_assist.data.mapper.NbaTransactionsMapper.map
import com.hami.sports_assist.data.network.service.NbaStatService
import com.hami.sports_assist.data.room.nba.*
import com.hami.sports_assist.data.util.DataValidationUtil.after
import com.hami.sports_assist.data.util.DataValidationUtil.dataMayOutdated
import com.hami.sports_assist.ui.component.DataStatus
import com.hami.sports_assist.util.LocalDateTimeUtil.getFirstDayOfWeek
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NbaStatRepository @Inject constructor(
    private val nbaStatService: NbaStatService,
    private val teamScheduleDao: TeamScheduleDao,
    private val standingDao: StandingDao,
    private val postSeasonDao: PostSeasonDao,
    private val nbaTransactionsDao: NbaTransactionsDao
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
                    fetchTeamScheduleFromBackend(team, it.dataVersion)
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

    suspend fun fetchTeamScheduleFromBackend(team: String, dataVersion: Long? = null) {
        withContext(Dispatchers.IO) {
            val response = nbaStatService.getTeamSchedule(team, dataVersion ?: -1)
            val data = response.body()
            when (response.code()) {
                HttpCode.HTTP_OK -> data?.let { teamScheduleDao.save(data.map(team)) }
                HttpCode.HTTP_DATA_UP_TO_DATE -> {
                    dataStatusChannel.send(DataStatus.DataIsUpToDate)
                    teamScheduleDao.update(System.currentTimeMillis())
                }
                else -> dataStatusChannel.send(DataStatus.ServiceError("Fetch schedules data error, code: ${response.code()}"))
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