package com.hami.sports_assist.data.room.nba

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hami.sports_assist.AppConfigurations.Room.API_VERSION
import java.util.Collections.emptyList

@Entity(tableName = "nba_team_schedule")
data class TeamScheduleEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val teamAbbr: String = "",
    val team: Team? = null,
    val timeStamp: Long,
    val dataVersion: Long = -1,
    val events: List<TeamEvent> = emptyList()
)

data class TeamEvent(
    val unixTimeStamp: Long,
    val eventType: String,
    val opponent: Team,
    val guestTeam: Team,
    val homeTeam: Team,
    val home: Boolean,
    val result: TeamResult? = null
)

data class TeamResult(
    val isWin: Boolean,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
)