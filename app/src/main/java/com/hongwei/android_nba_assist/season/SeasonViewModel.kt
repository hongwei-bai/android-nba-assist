package com.hongwei.android_nba_assist.season

import androidx.lifecycle.*
import com.hongwei.android_nba_assist.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.data.NbaStatRepository
import com.hongwei.android_nba_assist.data.league.nba.NbaSeasonStatus
import com.hongwei.android_nba_assist.season.common.SeasonStatus
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
        nbaStatRepository.getPostSeason()
            .map {
                when (it.currentStage) {
                    NbaSeasonStatus.Season.name -> SeasonStatus.RegularSeason
                    NbaSeasonStatus.PlayIn.name -> SeasonStatus.PlayInTournament
                    NbaSeasonStatus.PreSeason.name -> SeasonStatus.PreSeason
                    else -> SeasonStatus.PlayOff
                }
            }.asLiveData()

//    val westernPlayOff: LiveData<PlayOffStat> =
//        nbaStatRepository.getPostSeason().combine(nbaStatRepository.getStanding()) { postSeason, standing ->
//            postSeason.postSeason.western.map(standing.western, postSeason.playIn.western)
//        }.asLiveData()
//
//    val easternPlayOff: LiveData<PlayOffStat> =
//        nbaStatRepository.getPostSeason().combine(nbaStatRepository.getStanding()) { postSeason, standing ->
//            postSeason.playOff.eastern.map(standing.eastern, postSeason.playIn.eastern)
//        }.asLiveData()
//
//    val playOffGrandFinal: LiveData<PlayOffGrandFinalViewObject> =
//        nbaStatRepository.getPostSeason()
//            .map {
//                PlayOffGrandFinalViewObject(
//                    teamFromWestern = it.playOff.grandFinal.teamFromWestern,
//                    teamFromEastern = it.playOff.grandFinal.teamFromEastern,
//                    scoreWesternWinner = it.playOff.grandFinal.scoreWesternWinner,
//                    scoreEasternWinner = it.playOff.grandFinal.scoreEasternWinner,
//                    winner = it.playOff.grandFinal.winner
//                )
//            }.asLiveData()
//
//    val westernPlayIn: LiveData<PlayInStat> =
//        nbaStatRepository.getPostSeason().combine(nbaStatRepository.getStanding()) { postSeason, standing ->
//            PlayInStat(
//                teamsAbbr = standing.western.standings.subList(0, 10).map { it.team.abbrev.lowercase(Locale.US) },
//                winnerOf78 = postSeason.playIn.western.winnerOf78,
//                loserOf78 = postSeason.playIn.western.loserOf78,
//                winnerOf910 = postSeason.playIn.western.winnerOf910,
//                loserOf910 = postSeason.playIn.western.loserOf910,
//                lastWinner = postSeason.playIn.western.lastWinner
//            )
//        }.asLiveData()
//
//    val easternPlayIn: LiveData<PlayInStat> =
//        nbaStatRepository.getPostSeason().combine(nbaStatRepository.getStanding()) { postSeason, standing ->
//            PlayInStat(
//                teamsAbbr = standing.eastern.standings.subList(0, 10).map { it.team.abbrev.lowercase(Locale.US) },
//                winnerOf78 = postSeason.playIn.eastern.winnerOf78,
//                loserOf78 = postSeason.playIn.eastern.loserOf78,
//                winnerOf910 = postSeason.playIn.eastern.winnerOf910,
//                loserOf910 = postSeason.playIn.eastern.loserOf910,
//                lastWinner = postSeason.playIn.eastern.lastWinner
//            )
//        }.asLiveData()

    val westernStanding: LiveData<List<TeamStat>> =
        nbaStatRepository.getStanding().map {
            it.western.standings.map { entity ->
                TeamStat(
                    rank = entity.rank,
                    teamAbbr = entity.team.abbrev.lowercase(Locale.US),
                    team = entity.team.name,
                    logoResourceName = entity.team.abbrev.lowercase(Locale.US),
                    logoUrl = entity.team.logo,
                    wins = entity.wins,
                    losses = entity.losses,
                    gamesBack = entity.gamesBack,
                    currentStreak = entity.currentStreak,
                    last10Records = entity.last10Records
                )
            }
        }.asLiveData()

    val easternStanding: LiveData<List<TeamStat>> =
        nbaStatRepository.getStanding().map {
            it.eastern.standings.map { entity ->
                TeamStat(
                    rank = entity.rank,
                    teamAbbr = entity.team.abbrev.lowercase(Locale.US),
                    team = entity.team.name,
                    logoResourceName = entity.team.abbrev.lowercase(Locale.US),
                    logoUrl = entity.team.logo,
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