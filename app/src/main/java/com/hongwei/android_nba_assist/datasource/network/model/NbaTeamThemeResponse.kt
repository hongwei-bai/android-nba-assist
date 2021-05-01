package com.hongwei.android_nba_assist.datasource.network.model

data class NbaTeamThemeResponse(
    val dataVersion: Long,
    val team: String,
    val bannerUrl: String,
    val colorLight: Long?,
    val colorHome: Long?,
    val colorGuest: Long?
)