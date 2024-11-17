package com.mikeapp.sportsmate.season

import androidx.lifecycle.*
import com.mikeapp.sportsmate.ExceptionHelper.nbaExceptionHandler
import com.mikeapp.sportsmate.data.NbaStatRepository
import com.mikeapp.sportsmate.data.NbaTeamRepository
import com.mikeapp.sportsmate.data.league.nba.NbaSeasonStatusEnumResponse
import com.mikeapp.sportsmate.season.common.SeasonStatus
import com.mikeapp.sportsmate.season.common.SeasonStatusMapper.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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

    val seasonStatus: LiveData<SeasonStatus> =
        nbaTeamRepository.getTeamDetailFlow().mapNotNull {
            if (it.seasonStatus.isNotEmpty()) {
                NbaSeasonStatusEnumResponse.valueOf(it.seasonStatus).mapToUiState()
            } else {
                null
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