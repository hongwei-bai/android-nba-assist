package com.mikeapp.sportsmate.data.room.soccer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION
import java.util.Collections.emptyList

@Entity(tableName = "soccer_team_schedule")
data class SoccerTeamScheduleEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val teamId: Long = -1L,
    val teamAbbr: String = "",
    val teamDisplayName: String = "",
    val logo: String? = null,
    val location: String = "",
    val league: String = "",
    val timeStamp: Long = 0L,
    val dataVersion: Long = -1,
    val events: List<SoccerTeamEvent> = emptyList()
)

data class SoccerTeamEvent(
    val opponent: SoccerTeam,
    val homeTeam: SoccerTeam,
    val guestTeam: SoccerTeam,
    val unixTimeStamp: Long,
    val homeAwayEnum: SoccerHomeEnum,
    val completed: Boolean,
    val league: String,
    val result: SoccerResultEnum? = null,
    val score: String?,
    val otScore: String? = null,
    val penaltyScore: String? = null,
    val aggregateScore: String? = null,
    val winner: String? = null,
    val venue: Venue?
)

data class SoccerTeam(
    val teamId: Long,
    val abbrev: String,
    val displayName: String,
    val logo: String?,
    val location: String
)

data class Venue(
    val venue: String,
    val city: String?,
    val country: String?
)

enum class SoccerHomeEnum {
    Home, Away, Neutral
}

enum class SoccerResultEnum {
    FT, Tie, OT, Penalty, FirstLeg, Aggregate, AggregateAwayGoals
}