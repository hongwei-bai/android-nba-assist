package com.hongwei.android_nba_assist.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.local.AppSettings
import com.hongwei.android_nba_assist.data.room.TeamThemeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamDetailFlow(AppSettings.myTeam)
            .asLiveData(viewModelScope.coroutineContext)
}