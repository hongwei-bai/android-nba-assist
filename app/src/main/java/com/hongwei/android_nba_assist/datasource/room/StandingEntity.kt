package com.hongwei.android_nba_assist.datasource.room

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
        val western: List<TeamStanding>,
        @SerializedName("eastern")
        val eastern: List<TeamStanding>
)

data class TeamStanding(
        val rank: Int,
        val teamAbbr: String,
        val teamName: String,
        val wins: Int,
        val losses: Int,
        val gamesBack: Double,
        val currentStreak: Pair<String, Int>,
        val last10Records: Pair<Int, Int>
)