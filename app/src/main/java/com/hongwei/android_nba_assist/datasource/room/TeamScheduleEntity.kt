package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION
import java.util.Collections.emptyList

@Entity(tableName = "team_schedule")
data class TeamScheduleEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("team")
    val team: String = "",
    @SerializedName("time_stamp")
    val timeStamp: Long,
    @SerializedName("data_version")
    val dataVersion: Long = -1,
    @SerializedName("events")
    val events: List<Event> = emptyList()
)

data class Event(
    val unixTimeStamp: Long,
    val opponent: Team,
    val guestTeam: Team,
    val homeTeam: Team,
    val location: String,
    val home: Boolean,
    val result: Result? = null
)

data class Team(
    val abbrev: String,
    val logo: String
)

data class Result(
    val winLossSymbol: String,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
)