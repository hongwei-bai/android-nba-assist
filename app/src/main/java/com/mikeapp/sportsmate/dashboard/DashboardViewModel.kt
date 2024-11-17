package com.mikeapp.sportsmate.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.mikeapp.sportsmate.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.mikeapp.sportsmate.ExceptionHelper.nbaExceptionHandler
import com.mikeapp.sportsmate.dashboard.CountdownHelper.getUpcomingRange
import com.mikeapp.sportsmate.data.NbaStatRepository
import com.mikeapp.sportsmate.data.NbaTeamRepository
import com.mikeapp.sportsmate.data.SoccerStatRepository
import com.mikeapp.sportsmate.data.league.nba.NbaSeasonStatusEnumResponse
import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.season.common.SeasonStatus
import com.mikeapp.sportsmate.season.common.SeasonStatusMapper.mapToUiState
import com.mikeapp.sportsmate.ui.viewmodel.NbaViewObjectMapper.mapToDetailViewObject
import com.mikeapp.sportsmate.ui.viewmodel.NbaViewObjectMapper.mapToViewObject
import com.mikeapp.sportsmate.ui.viewmodel.SoccerViewObjectMapper.mapToDetailViewObject
import com.mikeapp.sportsmate.ui.viewmodel.SoccerViewObjectMapper.mapToViewObject
import com.mikeapp.sportsmate.util.LocalDateTimeUtil.getAheadOfHours
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
                NbaSeasonStatusEnumResponse.valueOf(it.seasonStatus).mapToUiState()
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