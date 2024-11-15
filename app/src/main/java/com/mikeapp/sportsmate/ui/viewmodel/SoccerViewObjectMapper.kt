package com.mikeapp.sportsmate.ui.viewmodel

import com.mikeapp.sportsmate.dashboard.EventDetailViewObject
import com.mikeapp.sportsmate.dashboard.EventViewObject
import com.mikeapp.sportsmate.dashboard.OpponentViewObject
import com.mikeapp.sportsmate.dashboard.TeamViewObject
import com.mikeapp.sportsmate.data.room.soccer.SoccerHomeEnum
import com.mikeapp.sportsmate.data.room.soccer.SoccerTeamEvent
import java.util.*

object SoccerViewObjectMapper {
    fun SoccerTeamEvent.mapToViewObject() =
        EventViewObject(
            unixTimeStamp = unixTimeStamp,
            opponent = OpponentViewObject(
                abbrev = opponent.abbrev.lowercase(Locale.US),
                name = opponent.displayName,
                logo = opponent.logo ?: ""
            ),
            opponentLocation = opponent.location,
            home = homeAwayEnum == SoccerHomeEnum.Home,
            result = "FT stub"
        )

    fun SoccerTeamEvent.mapToDetailViewObject() =
        EventDetailViewObject(
            unixTimeStamp = unixTimeStamp,
            guestTeam = TeamViewObject(
                abbrev = guestTeam.abbrev.lowercase(Locale.US),
                name = guestTeam.displayName,
                logo = guestTeam.logo ?: ""
            ),
            homeTeam = TeamViewObject(
                abbrev = homeTeam.abbrev.lowercase(Locale.US),
                name = homeTeam.displayName,
                logo = homeTeam.logo ?: ""
            )
        )
}