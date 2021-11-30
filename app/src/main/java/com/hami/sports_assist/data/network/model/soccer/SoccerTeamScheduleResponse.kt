package com.hami.sports_assist.data.network.model.soccer

data class SoccerTeamScheduleResponse(
    val dataVersion: Long,
    val teamId: Long,
    val teamAbbr: String,
    val teamDisplayName: String,
    val logo: String?,
    val location: String,
    val league: String,
    val events: List<SoccerTeamEventResponse>
)

data class SoccerTeamEventResponse(
    val opponent: SoccerTeamResponse,
    val unixTimeStamp: Long,
    val homeAway: String,
    val completed: Boolean,
    val league: String,
    val broadcasts: List<String> = emptyList(),
    val result: String? = null,
    val score: String?,
    val otScore: String? = null,
    val penaltyScore: String? = null,
    val aggregateScore: String? = null,
    val winner: String? = null,
    val venue: SoccerTeamVenueResponse?
)

data class SoccerTeamResponse(
    val teamId: Long,
    val abbrev: String,
    val displayName: String,
    val logo: String?,
    val location: String
)

data class SoccerTeamVenueResponse(
    val venue: String,
    val city: String?,
    val country: String?
)