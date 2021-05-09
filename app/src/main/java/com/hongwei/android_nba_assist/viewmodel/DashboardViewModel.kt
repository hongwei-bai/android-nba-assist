package com.hongwei.android_nba_assist.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.hongwei.android_nba_assist.datasource.DataSourceResult
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.room.Event
import com.hongwei.android_nba_assist.datasource.room.TeamScheduleEntity
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val localSettings: LocalSettings,
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val gamesLeft: MutableLiveData<Int> = MutableLiveData()

    val upcomingGameTime: MutableLiveData<Long> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            nbaStatRepository.getTeamSchedule(localSettings.myTeam).onEach {
                when (it) {
                    is DataSourceSuccessResult<TeamScheduleEntity> -> {
                        viewModelScope.launch(Dispatchers.Main) { gamesLeft.value = it.data.events.size }
                    }
                    else -> viewModelScope.launch(Dispatchers.Main) { gamesLeft.value = null }
                }
            }.collect()
        }
        viewModelScope.launch(Dispatchers.IO) {
            nbaStatRepository.getNextGameInfo(localSettings.myTeam)
                .filterIsInstance<DataSourceSuccessResult<Event>>()
                .onEach {
                    viewModelScope.launch(Dispatchers.Main) {
                        upcomingGameTime.value = it.data.unixTimeStamp
                    }
                }.collect()
        }
    }

    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamTheme(localSettings.myTeam)
            .filterIsInstance<DataSourceSuccessResult<TeamThemeEntity>>()
            .map { it.data }
            .asLiveData(viewModelScope.coroutineContext)

    val nextGameInfo: LiveData<DataSourceResult<Event>> =
        nbaStatRepository.getNextGameInfo(localSettings.myTeam).asLiveData()

    fun switchTeam(team: String) {
        viewModelScope.launch(Dispatchers.IO + ExceptionHelper.handler) {
            localSettings.myTeam = team
            nbaTeamRepository.fetchTeamThemeFromBackend(team)
        }
    }

    fun debugRoom() {
        viewModelScope.launch(Dispatchers.IO + ExceptionHelper.handler) {
            Log.d("bbbb", nbaTeamRepository.debug())
        }
    }
}