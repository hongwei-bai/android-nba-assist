package com.mikeapp.sportsmate.season

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.mikeapp.sportsmate.data.league.nba.Conference
import com.mikeapp.sportsmate.season.common.SeasonStatus

sealed class SeasonScreens(val icon: ImageVector) {
    object WestStanding : SeasonScreens(Icons.AutoMirrored.Outlined.List)
    object WestPlayIn : SeasonScreens(Icons.Outlined.PlayArrow)
    object WestPlayOff : SeasonScreens(Icons.Outlined.CheckCircle)
    object Final : SeasonScreens(Icons.Outlined.Star)
    object EastPlayOff : SeasonScreens(Icons.Outlined.CheckCircle)
    object EastPlayIn : SeasonScreens(Icons.Outlined.PlayArrow)
    object EastStanding : SeasonScreens(Icons.Outlined.List)

    companion object {
        fun fromSeasonStatus(seasonStatus: SeasonStatus, conference: Conference): SeasonScreens =
            when (conference) {
                Conference.Western -> when (seasonStatus) {
                    SeasonStatus.PlayInTournament -> WestPlayIn
                    SeasonStatus.PlayOff -> WestPlayOff
                    SeasonStatus.GrandFinal -> Final
                    else -> WestStanding
                }
                Conference.Eastern -> when (seasonStatus) {
                    SeasonStatus.PlayInTournament -> EastPlayIn
                    SeasonStatus.PlayOff -> EastPlayOff
                    SeasonStatus.GrandFinal -> Final
                    else -> EastStanding
                }
            }
    }
}