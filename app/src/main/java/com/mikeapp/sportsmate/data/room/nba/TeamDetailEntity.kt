package com.mikeapp.sportsmate.data.room.nba

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mikeapp.sportsmate.AppConfigurations.Room.API_VERSION

@Entity(tableName = "nba_team_detail")
data class TeamDetailEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val team: String = "",
    val teamFullName: String = "",
    val teamDisplayName: String = "",
    val city: String = "",
    val logoUrl: String = "",
    val bannerUrl: String = "",
    val backgroundUrl: String? = null,
    val colorLight: Long? = null,
    val colorHome: Long? = null,
    val colorGuest: Long? = null,
    var seasonStatus: String = ""
)

