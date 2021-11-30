package com.hami.sports_assist.season

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.hami.sports_assist.data.league.nba.Conference
import com.hami.sports_assist.season.common.SeasonStatus

sealed class SeasonScreens(val icon: ImageVector) {
    object WestStanding : SeasonScreens(Icons.Outlined.FormatListNumbered)
    object WestPlayIn : SeasonScreens(Icons.Outlined.FastForward)
    object WestPlayOff : SeasonScreens(Icons.Outlined.Mediation)
    object Final : SeasonScreens(Icons.Outlined.EmojiEvents)
    object EastPlayOff : SeasonScreens(Icons.Outlined.Mediation)
    object EastPlayIn : SeasonScreens(Icons.Outlined.FastRewind)
    object EastStanding : SeasonScreens(Icons.Outlined.FormatListNumbered)

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