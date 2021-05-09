package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.DataSourceSuccessResult
import com.hongwei.android_nba_assist.datasource.room.TeamThemeEntity
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localSettings: LocalSettings,
    private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamTheme(localSettings.myTeam)
            .filter { it is DataSourceSuccessResult }
            .map { (it as DataSourceSuccessResult).data }
            .asLiveData(viewModelScope.coroutineContext)
}