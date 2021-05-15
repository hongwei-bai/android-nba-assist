package com.hongwei.android_nba_assist.datasource.league.nba

enum class Conference {
    Eastern, Western
}

enum class Team {
    dal, den, det, gs, hou, ind, lac, lal, mem, mia, mil, min, no, ny, okc, orl,
    phi, phx, por, sa, sac, tor, uath, wsh
}

fun getConferenceByTeam(team: String): Conference = getConferenceByTeam(Team.valueOf(team))

fun getConferenceByTeam(team: Team): Conference = when (team) {
    Team.dal, Team.den, Team.gs, Team.hou, Team.lac, Team.lal, Team.mem, Team.min,
    Team.no, Team.okc, Team.phx, Team.por, Team.sa, Team.sac, Team.uath -> Conference.Western
    else -> Conference.Eastern
}