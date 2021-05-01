package com.hongwei.android_nba_assist.compat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.compat.usecase.ForceRequestScheduleUseCase
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.MatchEvent
import com.hongwei.android_nba_assist.compat.usecase.TeamScheduleUseCase
import com.hongwei.android_nba_assist.compat.viewmodel.viewobject.LoadingStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamCalendarViewModel @Inject constructor(
    private val forceRequestScheduleUseCase: ForceRequestScheduleUseCase,
    private val teamScheduleUseCase: TeamScheduleUseCase,
    private val exceptionHelper: ExceptionHelper
) : ViewModel() {
    val loadingStatus: MutableLiveData<LoadingStatus> by lazy {
        MutableLiveData<LoadingStatus>()
    }
    val matchEvents: MutableLiveData<List<MatchEvent>> by lazy {
        MutableLiveData<List<MatchEvent>>()
    }

    init {
        exceptionHelper.postHandler = {
            viewModelScope.launch(Dispatchers.Main) { loadingStatus.value = LoadingStatus.Error }
        }
    }

    fun load() {
        loadingStatus.value = LoadingStatus.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            withContext(Dispatchers.IO) {
                forceRequestScheduleUseCase.forceRequestScheduleFromServer()
            }
            loadCache()
        }
    }

    fun loadCache() {
        viewModelScope.launch(Dispatchers.Main + exceptionHelper.handler) {
            loadingStatus.value = LoadingStatus.Loading
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHelper.handler) {
            val upcomingSchedule = teamScheduleUseCase.getTeamSchedule()
            launch(Dispatchers.Main) {
                loadingStatus.value = LoadingStatus.Inactive
                matchEvents.value = upcomingSchedule
            }
        }
    }
}