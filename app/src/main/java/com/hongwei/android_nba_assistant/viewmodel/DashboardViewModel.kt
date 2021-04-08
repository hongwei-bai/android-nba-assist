package com.hongwei.android_nba_assistant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assistant.model.LocalSettings
import com.hongwei.android_nba_assistant.usecase.WarriorsCalendarUseCase
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil
import com.hongwei.android_nba_assistant.util.LocalDateTimeUtil.getBeginOfDay
import com.hongwei.android_nba_assistant.viewmodel.viewobject.UpcomingGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val warriorsCalendarUseCase: WarriorsCalendarUseCase,
    private val localSettings: LocalSettings,
    exceptionHelper: ExceptionHelper
) : ViewModel() {
    val loadingStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val upcomingGame: MutableLiveData<UpcomingGame?> by lazy {
        MutableLiveData<UpcomingGame?>()
    }

    init {
        loadingStatus.value = true
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            delay(500)
            val scheduleUpcoming = warriorsCalendarUseCase.getWarriorsSchedule().filter {
                it.date.after(getBeginOfDay(Calendar.getInstance()))
            }
            val upcomingOne = scheduleUpcoming.firstOrNull()
            launch(Dispatchers.Main) {
                loadingStatus.value = false
                upcomingGame.value = upcomingOne?.run {
                    UpcomingGame(
                        homeTeamShort = if (isHome) localSettings.myTeam else teamShort,
                        guestTeamShort = if (isHome) teamShort else localSettings.myTeam,
                        dateString = LocalDateTimeUtil.getLocalDateDisplay(date),
                        timeString = LocalDateTimeUtil.getLocalTimeDisplay(date),
                        inDays = LocalDateTimeUtil.getInDays(date),
                        gamesLeft = scheduleUpcoming.size
                    )
                }
            }
        }
    }
}