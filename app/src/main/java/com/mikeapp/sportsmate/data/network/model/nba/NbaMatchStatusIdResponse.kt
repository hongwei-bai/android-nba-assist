package com.mikeapp.sportsmate.data.network.model.nba

enum class NbaMatchStatusIdResponse(val value: Int) {
    NotStart(1), Finished(3), Postponed(6)
}