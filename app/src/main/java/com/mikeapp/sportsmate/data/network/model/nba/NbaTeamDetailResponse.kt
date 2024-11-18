package com.mikeapp.sportsmate.data.network.model.nba

data class NbaTeamDetailResponse(
    var team: String,
    var teamFullName: String,
    var teamDisplayName: String,
    var city: String,
    var logo: String,
    var banner: String,
    var background: String,
    var teamColorLight: Long,
    var teamColorHome: Long,
    var teamColorGuest: Long
)