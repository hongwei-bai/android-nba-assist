package com.mikeapp.sportsmate.data.network.model.nba

data class NbaTeamDetailResponse(
    var team: String,
    var displayName: String,
    var logo: String,
    var banner: String,
    var background: String,
    var teamColor: Long,
    var altColor: Long
)