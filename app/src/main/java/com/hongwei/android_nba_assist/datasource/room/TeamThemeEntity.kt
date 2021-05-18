package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION

@Entity(tableName = "team_theme")
data class TeamThemeEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("team")
    val team: String = "",
    @SerializedName("data_version")
    val dataVersion: Long = -1,
    @SerializedName("banner_url")
    val bannerUrl: String = "",
    @SerializedName("background_url")
    val backgroundUrl: String? = null,
    @SerializedName("color_light")
    val colorLight: Long? = null,
    @SerializedName("color_home")
    val colorHome: Long? = null,
    @SerializedName("color_guest")
    val colorGuest: Long? = null
)

