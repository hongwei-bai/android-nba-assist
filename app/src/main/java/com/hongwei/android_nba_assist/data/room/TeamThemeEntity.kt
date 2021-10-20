package com.hongwei.android_nba_assist.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION

@Entity(tableName = "team_theme")
data class TeamThemeEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val team: String = "",
    val logoUrl: String = "",
    val bannerUrl: String = "",
    val backgroundUrl: String? = null,
    val colorLight: Long? = null,
    val colorHome: Long? = null,
    val colorGuest: Long? = null
)

