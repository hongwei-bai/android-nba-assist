package com.mikeapp.sportsmate.season.common

import com.mikeapp.sportsmate.data.league.nba.NbaSeasonStatusEnumResponse

object SeasonStatusMapper {
    fun NbaSeasonStatusEnumResponse.mapToUiState(): SeasonStatus =
        when (this) {
            NbaSeasonStatusEnumResponse.Season -> SeasonStatus.RegularSeason
            NbaSeasonStatusEnumResponse.PlayIn -> SeasonStatus.PlayInTournament
            NbaSeasonStatusEnumResponse.PlayOffRound1,
            NbaSeasonStatusEnumResponse.PlayOffRound2,
            NbaSeasonStatusEnumResponse.PlayOffConferenceFinal -> SeasonStatus.PlayOff
            NbaSeasonStatusEnumResponse.PlayOffGrandFinal -> SeasonStatus.GrandFinal
            NbaSeasonStatusEnumResponse.PreSeason -> SeasonStatus.PreSeason
        }
}