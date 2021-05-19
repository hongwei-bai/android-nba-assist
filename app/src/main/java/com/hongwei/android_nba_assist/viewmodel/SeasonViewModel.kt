package com.hongwei.android_nba_assist.viewmodel

import androidx.lifecycle.*
import com.hongwei.android_nba_assist.repository.NbaStatRepository
import com.hongwei.android_nba_assist.view.season.PlayOffGrandFinalViewObject
import com.hongwei.android_nba_assist.view.season.RankedTeamViewObject
import com.hongwei.android_nba_assist.view.season.TeamViewObject
import com.hongwei.android_nba_assist.view.season.common.SeasonStatus
import com.hongwei.android_nba_assist.view.season.playin.PlayInViewObject
import com.hongwei.android_nba_assist.view.season.playoff.PlayOffViewObject
import com.hongwei.android_nba_assist.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.viewmodel.mapper.PlayOffViewObjectMapper.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = nbaStatRepository.dataStatus.asLiveData()

    val seasonStatus: LiveData<SeasonStatus> =
        nbaStatRepository.getPlayOff()
            .map {
                when {
                    it.playOffOngoing -> SeasonStatus.PlayOff
                    it.playInOngoing -> SeasonStatus.PlayInTournament
                    it.seasonOngoing -> SeasonStatus.RegularSeason
                    else -> SeasonStatus.End
                }
            }.asLiveData()

    val westernPlayOff: LiveData<PlayOffViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                it.playOff.western.map()
            }.asLiveData()

    val easternPlayOff: LiveData<PlayOffViewObject> =
        nbaStatRepository.getPlayOff()
            .map {
                it.playOff.eastern.map()
            }.asLiveData()

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
            }.asLiveData()

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
            }.asLiveData()

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
            }.asLiveData()

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
        }.asLiveData()

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
        }.asLiveData()

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