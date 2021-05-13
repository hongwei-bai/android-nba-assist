package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hongwei.android_nba_assist.datasource.local.Conference
import com.hongwei.android_nba_assist.datasource.local.LocalSettings
import com.hongwei.android_nba_assist.datasource.room.ConferenceStanding
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.TeamViewObject
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StandingViewModel @Inject constructor(
    localSettings: LocalSettings,
    nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val standing: LiveData<List<RankedTeamViewObject>> = nbaStatRepository.getStanding(localSettings.myTeam)
        .map {
            when (localSettings.conference) {
                Conference.Western -> it.western
                Conference.Eastern -> it.eastern
            }.standings.map { entity ->
                RankedTeamViewObject(
                    rank = entity.rank,
                    team = TeamViewObject(
                        abbrev = entity.team.abbrev.toLowerCase(Locale.US),
                        name = entity.team.name,
                        logo = entity.team.logo
                    ),
                    wins = entity.wins,
                    losses = entity.losses,
                    gamesBack = entity.gamesBack,
                    currentStreak = entity.currentStreak,
                    last10Records = entity.last10Records
                )
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