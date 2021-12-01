package com.hami.sports_assist.ui.viewmodel

import com.hami.sports_assist.dashboard.EventDetailViewObject
import com.hami.sports_assist.dashboard.EventViewObject
import com.hami.sports_assist.dashboard.OpponentViewObject
import com.hami.sports_assist.dashboard.TeamViewObject
import com.hami.sports_assist.data.room.soccer.SoccerHomeEnum
import com.hami.sports_assist.data.room.soccer.SoccerTeamEvent
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