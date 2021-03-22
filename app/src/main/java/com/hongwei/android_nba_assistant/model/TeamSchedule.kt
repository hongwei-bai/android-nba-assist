package com.hongwei.android_nba_assistant.model

data class TeamSchedule(
    val events: Events
)

data class Events(
    val pre: List<Pre>
)

data class Pre(
    val title: String,
    val group: List<Event>
)

data class Event(
    val date: Date,
    val opponent: Opponent,
    val time: Time,
    val result: Result
)

data class Date(
    val date: String,
    val format: String,
    val formatMobile: String,
    val isTimeTBD: Boolean
)

data class Opponent(
    val id: Int,
    val abbrev: String,
    val displayName: String,
    val logo: String,
    val recordSummary: String,
    val standingSummary: String,
    val location: String,
    val links: String,
    val homeAwaySymbol: String,
    val rank: String,
    val neutralSite: Boolean
)

data class Time(
    val time: String,
    val link: String,
    val state: String,
    val tbd: Boolean,
    val format: String
)

data class Result(
    val isTie: Boolean,
    val winLossSymbol: String,
    val link: String,
    val statusId: String
)