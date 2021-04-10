package com.hongwei.android_nba_assistant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assistant.usecase.MatchEvent
import com.hongwei.android_nba_assistant.usecase.TeamScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamCalendarViewModel @Inject constructor(
    private val teamScheduleUseCase: TeamScheduleUseCase,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {
    val matchEvents: MutableLiveData<List<MatchEvent>> by lazy {
        MutableLiveData<List<MatchEvent>>()
    }

    fun load() {
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            val upcomingSchedule = teamScheduleUseCase.getTeamSchedule()
            launch(Dispatchers.Main) {
                matchEvents.value = upcomingSchedule
            }
        }
    }
}