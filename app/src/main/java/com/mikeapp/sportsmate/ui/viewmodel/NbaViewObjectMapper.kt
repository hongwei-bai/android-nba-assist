package com.mikeapp.sportsmate.ui.viewmodel

import com.mikeapp.sportsmate.AppConfigurations
import com.mikeapp.sportsmate.dashboard.EventDetailViewObject
import com.mikeapp.sportsmate.dashboard.EventViewObject
import com.mikeapp.sportsmate.dashboard.OpponentViewObject
import com.mikeapp.sportsmate.dashboard.TeamViewObject
import com.mikeapp.sportsmate.data.room.nba.TeamEvent
import java.util.*

object NbaViewObjectMapper {
    fun TeamEvent.mapToViewObject() =
        EventViewObject(
            unixTimeStamp = unixTimeStamp,
            opponent = OpponentViewObject(
                abbrev = opponent.abbrev.lowercase(Locale.US),
                name = opponent.name,
                logo = opponent.logo
            ),
            opponentLocation = if (home) guestTeam.location else homeTeam.location,
            home = home,
            result = result?.let { resultEntity ->
                (if (resultEntity.isWin) AppConfigurations.View.WIN_SYMBOL else AppConfigurations.View.LOSE_SYMBOL) +
                        "${resultEntity.currentTeamScore}-${resultEntity.opponentTeamScore}"
            }
        )

    fun TeamEvent.mapToDetailViewObject() =
        EventDetailViewObject(
            unixTimeStamp = unixTimeStamp,
            guestTeam = TeamViewObject(
                abbrev = guestTeam.abbrev.lowercase(Locale.US),
                name = guestTeam.name,
                logo = guestTeam.logo
            ),
            homeTeam = TeamViewObject(
                abbrev = homeTeam.abbrev.lowercase(Locale.US),
                name = homeTeam.name,
                logo = homeTeam.logo
            )
        )
}