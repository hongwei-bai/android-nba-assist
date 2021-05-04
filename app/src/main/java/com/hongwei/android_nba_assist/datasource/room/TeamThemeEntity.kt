package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_theme")
data class TeamThemeEntity(
    @PrimaryKey
    val team: String = "",

    val dataVersion: Long = -1,

    val bannerUrl: String = "",

    val colorLight: Long? = null,

    val colorHome: Long? = null,

    val colorGuest: Long? = null
)

