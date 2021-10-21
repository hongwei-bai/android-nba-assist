package com.hongwei.android_nba_assist.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.local.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    var preFetchCompleted = MutableLiveData(false)

    init {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            nbaTeamRepository.fetchTeamDetailFromBackend(AppSettings.myTeam)
            nbaTeamRepository.fetchSeasonStatusFromBackend()
            preFetchCompleted.postValue(true)
        }
    }
}