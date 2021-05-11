package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.Conference
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.room.ConferenceStanding
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class StandingViewModel @Inject constructor(
    localSettings: LocalSettings,
    nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val standing: LiveData<ConferenceStanding> = nbaStatRepository.getStanding(localSettings.myTeam)
        .map {
            when (localSettings.conference) {
                Conference.Western -> it.western
                Conference.Eastern -> it.eastern
            }
        }
        .asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val theOtherConferenceStanding: LiveData<ConferenceStanding> = nbaStatRepository.getStanding(localSettings.myTeam)
        .map {
            when (localSettings.conference) {
                Conference.Western -> it.eastern
                Conference.Eastern -> it.western
            }
        }
        .asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)
}