package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
        private val localSettings: LocalSettings,
        private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    fun preload(onPreloadComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + ExceptionHelper.handler) {
            nbaTeamRepository.fetchTeamThemeFromBackend(localSettings.myTeam)
            viewModelScope.launch(Dispatchers.Main + ExceptionHelper.handler) {
                onPreloadComplete.invoke()
            }
        }
    }
}