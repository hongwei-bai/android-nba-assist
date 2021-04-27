package com.hongwei.android_nba_assist.datasource.network.model

data class StandingData(
    val dataVersion: Long = 0,
    val western: ConferenceStandingData,
    val eastern: ConferenceStandingData
)

data class ConferenceStandingData(
    val teams: MutableList<TeamStandingData>
)

data class TeamStandingData(
    val rank: Int,
    val teamAbbr: String,
    val teamName: String,
    val wins: Int,
    val losses: Int,
    val pct: Double,
    val gamesBack: Double,
    val homeRecords: PairInt,
    val awayRecords: PairInt,
    val divisionRecords: PairInt,
    val conferenceRecords: PairInt,
    val pointsPerGame: Double,
    val opponentPointsPerGame: Double,
    val avePointsDiff: Double,
    val currentStreak: PairStringInt,
    val last10Records: PairInt
)

data class PairInt(
    val first: Int,
    val second: Int
) {
    fun toPair(): Pair<Int, Int> = Pair(first, second)
}

data class PairStringInt(
    val first: String,
    val second: Int
) {
    fun toPair(): Pair<String, Int> = Pair(first, second)
}