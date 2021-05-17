package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.AppSettings
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    fun preload(onPreloadComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            delay(20)
            nbaTeamRepository.fetchTeamThemeFromBackend(AppSettings.myTeam)
            viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) {
                onPreloadComplete.invoke()
            }
        }
    }
}