package com.mikeapp.sportsmate.data.room.nba

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION

@Entity(tableName = "nba_standing")
data class StandingEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val timeStamp: Long,
    val dataVersion: Long = -1,
    val western: ConferenceStanding,
    val eastern: ConferenceStanding
)

data class ConferenceStanding(
    val conferenceTitle: String,
    val standings: List<TeamStanding>
)

data class TeamStanding(
    val rank: Int,
    val team: Team,
    val wins: Int,
    val losses: Int,
    val gamesBack: Double,
    val currentStreak: Pair<String, Int>,
    val last10Records: Pair<Int, Int>
)