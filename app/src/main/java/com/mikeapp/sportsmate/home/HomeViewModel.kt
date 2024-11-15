package com.mikeapp.sportsmate.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mikeapp.sportsmate.data.NbaTeamRepository
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity
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