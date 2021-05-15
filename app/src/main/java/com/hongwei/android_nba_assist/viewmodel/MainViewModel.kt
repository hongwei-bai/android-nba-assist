package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamTheme(AppSettings.myTeam)
            .asLiveData(viewModelScope.coroutineContext)
}