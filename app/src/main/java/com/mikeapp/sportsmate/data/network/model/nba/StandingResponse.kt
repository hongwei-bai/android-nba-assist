package com.mikeapp.sportsmate.data.network.model.nba

data class StandingResponse(
    val dataVersion: Long = 0,
    val western: List<TeamStandingResponse>,
    val eastern: List<TeamStandingResponse>
)

data class TeamStandingResponse(
    val rank: Int,
    val teamAbbr: String,
    val teamName: String,
    val teamLogo: String,
    val teamLocation: String,
    val wins: Int,
    val losses: Int,
    val pct: Double,
    val gamesBack: Double,
    val homeRecords: PairIntResponse,
    val awayRecords: PairIntResponse,
    val divisionRecords: PairIntResponse,
    val conferenceRecords: PairIntResponse,
    val pointsPerGame: Double,
    val opponentPointsPerGame: Double,
    val avePointsDiff: Double,
    val currentStreak: PairStringIntResponse,
    val last10Records: PairIntResponse
)

data class PairIntResponse(
    val first: Int,
    val second: Int
) {
    fun toPair(): Pair<Int, Int> = Pair(first, second)
}

data class PairStringIntResponse(
    val first: String,
    val second: Int
) {
    fun toPair(): Pair<String, Int> = Pair(first, second)
}