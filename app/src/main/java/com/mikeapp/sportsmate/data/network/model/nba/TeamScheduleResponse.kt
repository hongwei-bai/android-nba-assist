package com.mikeapp.sportsmate.data.network.model.nba

import kotlinx.serialization.Serializable

@Serializable
data class TeamScheduleResponse(
    val dataVersion: Long,
    val teamSchedule: List<Season>
)

@Serializable
data class Season(
    val season: Int,
    val seasonType: SeasonType,
    val title: String,
    val notes: String,
    val events: Events
)

@Serializable
data class SeasonType(
    val id: String,
    val type: Int,
    val name: String,
    val abbreviation: String
)

@Serializable
data class Events(
    val pre: List<Event>,
    val post: List<Event>
)

@Serializable
data class Event(
    val date: EventDate,
    val opponent: Opponent,
    val time: EventTime,
    val tickets: Tickets?,
    val network: List<Network>,
    val result: Result,
    val timeAndNetwork: TimeAndNetwork,
    val record: String,
    val seasonType: SeasonType,
    val seasonYear: Int,
    val status: Status,
    val notes: Notes?,
    val competitionKey: String,
    val competitionName: String,
    val leaderPoints: LeaderStat,
    val leaderRebounds: LeaderStat,
    val leaderAssists: LeaderStat
)

@Serializable
data class EventDate(
    val date: String,
    val format: String,
    val formatMobile: String,
    val isTimeTBD: Boolean,
    val isTBDFlex: Boolean
)

@Serializable
data class Opponent(
    val id: String,
    val abbrev: String,
    val displayName: String,
    val shortDisplayName: String,
    val logo: String,
    val recordSummary: String,
    val standingSummary: String,
    val location: String,
    val links: String,
    val homeAwaySymbol: String,
    val rank: String,
    val neutralSite: Boolean
)

@Serializable
data class EventTime(
    val time: String,
    val link: String?,
    val state: String,
    val tbd: Boolean,
    val isTBDFlex: Boolean,
    val format: String
)

@Serializable
data class Tickets(
    val summary: String?,
    val link: String?
)

@Serializable
data class Network(
    val name: String
)

@Serializable
data class Result(
    val winner: Boolean? = null,
    val isTie: Boolean,
    val winLossSymbol: String,
    val currentTeamScore: String,
    val opponentTeamScore: String,
    val link: String?,
    val statusId: String
)

@Serializable
data class TimeAndNetwork(
    val time: EventTime,
    val network: List<Network>
)

@Serializable
data class Status(
    val id: String,
    val name: String,
    val state: String,
    val completed: Boolean,
    val description: String,
    val detail: String,
    val shortDetail: String
)

@Serializable
data class Notes(
    val type: String? = null,
    val headline: String? = null
)

@Serializable
data class LeaderStat(
    val athlete: Athlete,
    val value: Int
)

@Serializable
data class Athlete(
    val shortName: String,
    val name: String,
    val href: String,
    val uid: String,
    val lastName: String
)