package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.*
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.view.season.PlayOffGrandFinalViewObject
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffViewObject
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.TeamViewObject
import com.hongwei.android_nba_assist.view.season.playin.PlayInViewObject
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.viewmodel.mapper.PlayOffViewObjectMapper.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(
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

    val westernPlayOff: LiveData<PlayOffViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                it.playOff.western.map()
            }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val easternPlayOff: LiveData<PlayOffViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                it.playOff.eastern.map()
            }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val playOffGrandFinal: LiveData<PlayOffGrandFinalViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                PlayOffGrandFinalViewObject(
                    teamFromWestern = it.playOff.grandFinal.teamFromWestern,
                    teamFromEastern = it.playOff.grandFinal.teamFromEastern,
                    scoreWesternWinner = it.playOff.grandFinal.scoreWesternWinner,
                    scoreEasternWinner = it.playOff.grandFinal.scoreEasternWinner,
                    winner = it.playOff.grandFinal.winner
                )
            }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val westernPlayIn: LiveData<PlayInViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                PlayInViewObject(
                    winnerOf78 = it.playIn.western.winnerOf78,
                    loserOf78 = it.playIn.western.loserOf78,
                    winnerOf910 = it.playIn.western.winnerOf910,
                    loserOf910 = it.playIn.western.loserOf910,
                    lastWinner = it.playIn.western.lastWinner
                )
            }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

    val easternPlayIn: LiveData<PlayInViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                PlayInViewObject(
                    winnerOf78 = it.playIn.eastern.winnerOf78,
                    loserOf78 = it.playIn.eastern.loserOf78,
                    winnerOf910 = it.playIn.eastern.winnerOf910,
                    loserOf910 = it.playIn.eastern.loserOf910,
                    lastWinner = it.playIn.eastern.lastWinner
                )
            }.asLiveData(viewModelScope.coroutineContext + nbaExceptionHandler)

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
            val jobs = listOf(
                async { nbaStatRepository.fetchStandingFromBackend() },
                async { nbaStatRepository.fetchPlayOffFromBackend() }
            )
            jobs.awaitAll()
            isRefreshing.postValue(false)
        }
    }
}