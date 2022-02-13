package com.hami.sports_assist.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.hami.sports_assist.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hami.sports_assist.ExceptionHelper.nbaExceptionHandler
import com.hami.sports_assist.dashboard.CountdownHelper.getUpcomingRange
import com.hami.sports_assist.data.NbaStatRepository
import com.hami.sports_assist.data.NbaTeamRepository
import com.hami.sports_assist.data.SoccerStatRepository
import com.hami.sports_assist.data.league.nba.NbaSeasonStatusResponse
import com.hami.sports_assist.data.local.AppSettings
import com.hami.sports_assist.season.common.SeasonStatus
import com.hami.sports_assist.season.common.SeasonStatusMapper.mapToUiState
import com.hami.sports_assist.ui.viewmodel.NbaViewObjectMapper.mapToDetailViewObject
import com.hami.sports_assist.ui.viewmodel.NbaViewObjectMapper.mapToViewObject
import com.hami.sports_assist.ui.viewmodel.SoccerViewObjectMapper.mapToDetailViewObject
import com.hami.sports_assist.ui.viewmodel.SoccerViewObjectMapper.mapToViewObject
import com.hami.sports_assist.util.LocalDateTimeUtil.getAheadOfHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository,
    private val soccerStatRepository: SoccerStatRepository,
    private val upcomingGameCounter: UpcomingGameCounter
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = merge(
        nbaStatRepository.dataStatus,
        soccerStatRepository.dataStatus
    ).asLiveData(viewModelScope.coroutineContext)

    val seasonStatus: LiveData<SeasonStatus> =
        nbaTeamRepository.getTeamDetailFlow().mapNotNull {
            if (it.seasonStatus.isNotEmpty()) {
                NbaSeasonStatusResponse.valueOf(it.seasonStatus).mapToUiState()
            } else {
                null
            }
        }.asLiveData(viewModelScope.coroutineContext)

    val gamesLeft: MutableLiveData<Int> = MutableLiveData()

    val upcomingGameTime: MutableLiveData<Long> = MutableLiveData()

    val countdownString: MutableLiveData<String> = MutableLiveData()

    val myNbaTeam: MutableLiveData<String> = MutableLiveData()

    val teamBackground: LiveData<String?> =
        nbaTeamRepository.getTeamDetailFlow()
            .map { it.backgroundUrl }
            .asLiveData(viewModelScope.coroutineContext)

    val nextGameInfo: LiveData<EventDetailViewObject> =
        nbaStatRepository.getNextGameInfo(AppSettings.myNbaTeam)
            .map { it.mapToDetailViewObject() }
            .combine(
                soccerStatRepository.getNextGameInfo(AppSettings.myEuroSoccerTeam)
                    .map { it.mapToDetailViewObject() }
            ) { nextNbaEvent, nextSoccerEvent ->
                if (nextNbaEvent.unixTimeStamp < nextSoccerEvent.unixTimeStamp) {
                    nextNbaEvent
                } else {
                    nextSoccerEvent
                }
            }.asLiveData(viewModelScope.coroutineContext)

    val upcomingGames: LiveData<List<EventViewObject>> = nbaStatRepository.getTeamSchedule(AppSettings.myNbaTeam)
        .map { teamEvents ->
            teamEvents.map { it.mapToViewObject() }
        }.asLiveData(viewModelScope.coroutineContext)

    val upcomingGamesSecondary: LiveData<List<EventViewObject>> = soccerStatRepository.getTeamSchedule(AppSettings.myEuroSoccerTeam)
        .map { teamEvents ->
            teamEvents.map { it.mapToViewObject() }
        }.asLiveData(viewModelScope.coroutineContext)

    val calendarDays: MutableLiveData<List<List<Calendar>>> = MutableLiveData()

    init {
        myNbaTeam.value = AppSettings.myNbaTeam
        upcomingGameCounter.countdownCallback = { countdownString.postValue(it) }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeScheduleFlow() }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeNextGameFlow() }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            nbaStatRepository.fetchTeamScheduleFromBackend(AppSettings.myNbaTeam)
            soccerStatRepository.fetchSoccerTeamScheduleFromBackend(AppSettings.myEuroSoccerTeam)
            isRefreshing.postValue(false)
        }
    }

    private suspend fun subscribeScheduleFlow() {
        nbaStatRepository.getTeamSchedule(AppSettings.myNbaTeam).onEach {
            gamesLeft.postValue(it.filter { event -> after(event.unixTimeStamp) }.size)
            calendarDays.postValue(
                GenerateCalendarHelper
                    .generateCalendarDays(it, AppSettings.scheduleWeeks, AppSettings.weekStartsFromMonday)
            )
        }.collect()
    }

    private suspend fun subscribeNextGameFlow() {
        nbaStatRepository.getNextGameInfo(AppSettings.myNbaTeam)
            .onEach {
                val eventTime = it.unixTimeStamp
                upcomingGameTime.postValue(eventTime)
                when (getUpcomingRange(eventTime)) {
                    UpcomingRange.CountingDown -> {
                        upcomingGameCounter.startCountDown(viewModelScope, eventTime)
                    }
                    UpcomingRange.CountingUp -> {
                        upcomingGameCounter.startCountUp(viewModelScope, eventTime)
                    }
                    else -> Unit
                }
            }.collect()
    }

    private fun after(unixTimeStamp: Long): Boolean = Calendar.getInstance().apply {
        timeInMillis = unixTimeStamp
    }.after(getAheadOfHours(IGNORE_TODAY_S_GAME_FROM_HOURS))

    fun debugRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("bbbb", nbaTeamRepository.debug())
        }
    }
}