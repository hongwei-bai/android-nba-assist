package com.hongwei.android_nba_assist.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.local.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    private var onStart = true

    fun preload(onPreloadComplete: () -> Unit) {
        if (!onStart) return
        onStart = false
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            delay(20)
            nbaTeamRepository.fetchTeamDetailFromBackend(AppSettings.myTeam)
            viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) {
                onPreloadComplete.invoke()
            }
        }
    }
}