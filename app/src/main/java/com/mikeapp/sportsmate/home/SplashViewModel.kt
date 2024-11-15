package com.mikeapp.sportsmate.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikeapp.sportsmate.ExceptionHelper.nbaExceptionHandler
import com.mikeapp.sportsmate.data.NbaTeamRepository
import com.mikeapp.sportsmate.data.local.AppSettings
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
            nbaTeamRepository.fetchTeamDetailFromBackend(AppSettings.myNbaTeam)
            nbaTeamRepository.fetchSeasonStatusFromBackend()
            preFetchCompleted.postValue(true)
        }
    }
}