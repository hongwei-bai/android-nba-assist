package com.hongwei.android_nba_assist.season

import androidx.lifecycle.*
import com.hongwei.android_nba_assist.ExceptionHelper.nbaExceptionHandler
import com.hongwei.android_nba_assist.data.NbaStatRepository
import com.hongwei.android_nba_assist.data.NbaTeamRepository
import com.hongwei.android_nba_assist.data.league.nba.NbaSeasonStatusResponse
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
    private val nbaTeamRepository: NbaTeamRepository,
    private val nbaStatRepository: NbaStatRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val dataStatus = nbaStatRepository.dataStatus.asLiveData()

    val seasonStatusResponse: LiveData<SeasonStatus> =
        nbaTeamRepository.getTeamDetailFlow().map {
            when (NbaSeasonStatusResponse.valueOf(it.seasonStatus)) {
                NbaSeasonStatusResponse.Season -> SeasonStatus.RegularSeason
                NbaSeasonStatusResponse.PlayIn -> SeasonStatus.PlayInTournament
                NbaSeasonStatusResponse.PlayOffRound1,
                NbaSeasonStatusResponse.PlayOffRound2,
                NbaSeasonStatusResponse.PlayOffConferenceFinal -> SeasonStatus.PlayOff
                NbaSeasonStatusResponse.PlayOffGrandFinal -> SeasonStatus.GrandFinal
                NbaSeasonStatusResponse.PreSeason -> SeasonStatus.PreSeason
            }
        }.asLiveData()

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
                async { nbaStatRepository.fetchPostSeasonFromBackend() }
            )
            jobs.awaitAll()
            isRefreshing.postValue(false)
        }
    }
}