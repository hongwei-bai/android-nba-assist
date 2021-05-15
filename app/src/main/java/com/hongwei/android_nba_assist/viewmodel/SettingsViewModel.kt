package com.hongwei.android_nba_assist.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val teamBanner: LiveData<String> =
        nbaTeamRepository.getTeamTheme(AppSettings.myTeam)
            .map { it.bannerUrl }
            .asLiveData(viewModelScope.coroutineContext)

    fun switchTeam(context: Context, team: String) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            AppSettings.setTeam(context, team)
            nbaTeamRepository.fetchTeamThemeFromBackend(team)
            nbaStatRepository.fetchTeamScheduleFromBackend(team)
            nbaStatRepository.fetchStandingFromBackend()
        }
    }
}