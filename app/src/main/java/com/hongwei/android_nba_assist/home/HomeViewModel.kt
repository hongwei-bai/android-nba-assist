package com.hongwei.android_nba_assist.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.room.nba.TeamDetailEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamDetailEntity> =
        nbaTeamRepository.getTeamDetailFlow()
            .asLiveData(viewModelScope.coroutineContext)
}