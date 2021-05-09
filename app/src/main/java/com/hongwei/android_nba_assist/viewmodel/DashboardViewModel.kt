package com.hongwei.android_nba_assist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
        private val localSettings: LocalSettings,
        private val nbaTeamRepository: NbaTeamRepository,
        private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamThemeEntity> =
            nbaTeamRepository.getTeamTheme(localSettings.myTeam)
                    .filterIsInstance<DataSourceSuccessResult<TeamThemeEntity>>()
                    .map { it.data }
                    .asLiveData(viewModelScope.coroutineContext)

//    val nextGame : LiveData<DataSourceResult<EventResponse>> =
//            nbaStatRepository.getTeamSchedule(localSettings.myTeam)

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