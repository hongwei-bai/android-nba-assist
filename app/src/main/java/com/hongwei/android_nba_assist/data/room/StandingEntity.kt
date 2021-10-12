package com.hongwei.android_nba_assist.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION

@Entity(tableName = "standing")
data class StandingEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("time_stamp")
    val timeStamp: Long,
    @SerializedName("data_version")
    val dataVersion: Long = -1,
    @SerializedName("western")
    val western: ConferenceStanding,
    @SerializedName("eastern")
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