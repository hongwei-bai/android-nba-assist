package com.hami.sports_assist.season.common

import com.hami.sports_assist.data.league.nba.NbaSeasonStatusResponse

object SeasonStatusMapper {
    fun NbaSeasonStatusResponse.mapToUiState(): SeasonStatus =
        when (this) {
            NbaSeasonStatusResponse.Season -> SeasonStatus.RegularSeason
            NbaSeasonStatusResponse.PlayIn -> SeasonStatus.PlayInTournament
            NbaSeasonStatusResponse.PlayOffRound1,
            NbaSeasonStatusResponse.PlayOffRound2,
            NbaSeasonStatusResponse.PlayOffConferenceFinal -> SeasonStatus.PlayOff
            NbaSeasonStatusResponse.PlayOffGrandFinal -> SeasonStatus.GrandFinal
            NbaSeasonStatusResponse.PreSeason -> SeasonStatus.PreSeason
        }
}