package com.hongwei.android_nba_assist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.hongwei.android_nba_assist.constant.AppConfigurations.TeamScheduleConfiguration.IGNORE_TODAY_S_GAME_FROM_HOURS
import com.hongwei.android_nba_assist.datasource.DataSourceResult
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.room.Event
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil.getAheadOfHours
import com.hongwei.android_nba_assist.viewmodel.helper.CountdownHelper.getUpcomingRange
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.viewmodel.helper.GenerateCalendarHelper
import com.hongwei.android_nba_assist.viewmodel.helper.UpcomingGameCounter
import com.hongwei.android_nba_assist.viewmodel.helper.UpcomingRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val localSettings: LocalSettings,
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository,
    private val upcomingGameCounter: UpcomingGameCounter
) : ViewModel() {
    val gamesLeft: MutableLiveData<Int> = MutableLiveData()

    val upcomingGameTime: MutableLiveData<Long> = MutableLiveData()

    val countdownString: MutableLiveData<String> = MutableLiveData()

    val myTeam: MutableLiveData<String> = MutableLiveData()

    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamTheme(localSettings.myTeam)
            .filterIsInstance<DataSourceSuccessResult<TeamThemeEntity>>()
            .map { it.data }
            .asLiveData(viewModelScope.coroutineContext)

    val nextGameInfo: LiveData<DataSourceResult<Event>> =
        nbaStatRepository.getNextGameInfo(localSettings.myTeam).asLiveData()

    val upcomingGames: LiveData<List<Event>> =
        nbaStatRepository.getTeamSchedule(localSettings.myTeam)
            .filterIsInstance<DataSourceSuccessResult<List<Event>>>()
            .map { it.data }
            .asLiveData(viewModelScope.coroutineContext)

    val calendarDays: MutableLiveData<List<List<Calendar>>> = MutableLiveData()

    init {
        myTeam.value = localSettings.myTeam
        upcomingGameCounter.countdownCallback = { viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) { countdownString.value = it } }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeScheduleFlow() }
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) { subscribeNextGameFlow() }
    }

    private suspend fun subscribeScheduleFlow() {
        nbaStatRepository.getTeamSchedule(localSettings.myTeam).onEach {
            when (it) {
                is DataSourceSuccessResult<List<Event>> -> {
                    viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) {
                        gamesLeft.value = it.data.filter { event ->
                            after(event.unixTimeStamp)
                        }.size
                        calendarDays.value = GenerateCalendarHelper.generateCalendarDays(it.data, localSettings)
                    }
                }
                else -> viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) { gamesLeft.value = null }
            }
        }.collect()
    }

    private suspend fun subscribeNextGameFlow() {
        nbaStatRepository.getNextGameInfo(localSettings.myTeam)
            .filterIsInstance<DataSourceSuccessResult<Event>>()
            .onEach {
                viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) {
                    val eventTime = it.data.unixTimeStamp
                    upcomingGameTime.value = eventTime
                    when (getUpcomingRange(eventTime)) {
                        UpcomingRange.CountingDown -> {
                            upcomingGameCounter.startCountDown(eventTime)
                        }
                        UpcomingRange.CountingUp -> {
                            upcomingGameCounter.startCountUp(eventTime)
                        }
                    }
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