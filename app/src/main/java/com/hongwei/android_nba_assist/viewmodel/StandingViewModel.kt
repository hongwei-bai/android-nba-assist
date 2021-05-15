package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.*
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.TeamViewObject
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StandingViewModel @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = liveData {
        nbaStatRepository.dataStatus.collect {
            emit(it)
            delay(3000)
            emit(null)
        }
    }

    val westernStanding: LiveData<List<RankedTeamViewObject>> =
        nbaStatRepository.getStanding().map {
            it.western.standings.map { entity ->
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
        }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val easternStanding: LiveData<List<RankedTeamViewObject>> =
        nbaStatRepository.getStanding().map {
            it.eastern.standings.map { entity ->
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
        }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            nbaStatRepository.fetchStandingFromBackend()
            isRefreshing.postValue(false)
        }
    }
}