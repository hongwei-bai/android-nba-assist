package com.hongwei.android_nba_assist.dashboard

import android.util.Log
import androidx.lifecycle.*
import com.hongwei.android_nba_assist.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.dashboard.CountdownHelper.getUpcomingRange
import com.hongwei.android_nba_assist.data.NbaStatRepository
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.local.AppSettings
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository,
    private val upcomingGameCounter: UpcomingGameCounter
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = nbaStatRepository.dataStatus.asLiveData()

    val gamesLeft: MutableLiveData<Int> = MutableLiveData()

    val upcomingGameTime: MutableLiveData<Long> = MutableLiveData()

    val countdownString: MutableLiveData<String> = MutableLiveData()

    val myTeam: MutableLiveData<String> = MutableLiveData()

    val teamBackground: LiveData<String?> =
        nbaTeamRepository.getTeamDetail(AppSettings.myTeam)
            .map { it.backgroundUrl }
            .asLiveData(viewModelScope.coroutineContext)

    val nextGameInfo: LiveData<EventDetailViewObject> =
        nbaStatRepository.getNextGameInfo(AppSettings.myTeam)
            .map {
                EventDetailViewObject(
                    unixTimeStamp = it.unixTimeStamp,
                    guestTeam = TeamViewObject(
                        abbrev = it.guestTeam.abbrev.lowercase(Locale.US),
                        name = it.guestTeam.name,
                        logo = it.guestTeam.logo
                    ),
                    homeTeam = TeamViewObject(
                        abbrev = it.homeTeam.abbrev.lowercase(Locale.US),
                        name = it.homeTeam.name,
                        logo = it.homeTeam.logo
                    )
                )
            }.asLiveData(viewModelScope.coroutineContext)

    val upcomingGames: LiveData<List<EventViewObject>> =
        nbaStatRepository.getTeamSchedule(AppSettings.myTeam)
            .map {
                it.map { entity ->
                    EventViewObject(
                        unixTimeStamp = entity.unixTimeStamp,
                        opponent = OpponentViewObject(
                            abbrev = entity.opponent.abbrev.lowercase(Locale.US),
                            name = entity.opponent.name,
                            logo = entity.opponent.logo
                        ),
                        location = entity.location,
                        home = entity.home,
                        result = entity.result?.let { resultEntity ->
                            "${resultEntity.winLossSymbol}${resultEntity.currentTeamScore}-${resultEntity.opponentTeamScore}"
                        }
                    )
                }
            }
            .asLiveData(viewModelScope.coroutineContext)

    val calendarDays: MutableLiveData<List<List<Calendar>>> = MutableLiveData()

    init {
        myTeam.value = AppSettings.myTeam
        upcomingGameCounter.countdownCallback = { countdownString.postValue(it) }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeScheduleFlow() }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeNextGameFlow() }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            nbaStatRepository.fetchTeamScheduleFromBackend(AppSettings.myTeam)
            isRefreshing.postValue(false)
        }
    }

    private suspend fun subscribeScheduleFlow() {
        nbaStatRepository.getTeamSchedule(AppSettings.myTeam).onEach {
            gamesLeft.postValue(it.filter { event -> after(event.unixTimeStamp) }.size)
            calendarDays.postValue(
                GenerateCalendarHelper
                    .generateCalendarDays(it, AppSettings.scheduleWeeks, AppSettings.weekStartsFromMonday)
            )
        }.collect()
    }

    private suspend fun subscribeNextGameFlow() {
        nbaStatRepository.getNextGameInfo(AppSettings.myTeam)
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