package com.mikeapp.sportsmate.data.league.nba

enum class Conference {
    Eastern, Western
}

enum class Team {
    atl, bkn, bos, cha, chi, cle, dal, den, det, gs, hou, ind, lac, lal, mem, mia, mil, min, no, ny, okc, orl,
    phi, phx, por, sa, sac, tor, utah, wsh
}

val westernTeams = listOf(
    Team.dal.name, Team.den.name, Team.gs.name, Team.hou.name, Team.lac.name, Team.lal.name, Team.mem.name, Team.min.name, Team.no.name,
    Team.okc.name, Team.phx.name, Team.por.name, Team.sa.name, Team.sac.name, Team.utah.name,
)

val easternTeams = listOf(
    Team.atl.name, Team.bkn.name, Team.bos.name, Team.cha.name, Team.chi.name, Team.cle.name, Team.det.name, Team.ind.name, Team.mia.name,
    Team.mil.name, Team.ny.name, Team.orl.name, Team.phi.name, Team.tor.name, Team.wsh.name
)

fun getConferenceByTeam(team: String): Conference = getConferenceByTeam(Team.valueOf(team))

fun getConferenceByTeam(team: Team): Conference = when (team) {
    Team.dal, Team.den, Team.gs, Team.hou, Team.lac, Team.lal, Team.mem, Team.min,
    Team.no, Team.okc, Team.phx, Team.por, Team.sa, Team.sac, Team.utah -> Conference.Western
    else -> Conference.Eastern
}