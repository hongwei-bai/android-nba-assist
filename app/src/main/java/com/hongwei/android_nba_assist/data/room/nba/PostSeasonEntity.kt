package com.hongwei.android_nba_assist.data.room.nba

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hongwei.android_nba_assist.AppConfigurations.Room.API_VERSION

@Entity(tableName = "nba_post_season")
data class PostSeasonEntity(
    @PrimaryKey
    var apiVersion: Int = API_VERSION,
    var dataVersion: Long = 0,
    var timeStamp: Long = 0,
    var currentStage: String = "",
    var westernPlayInEventRound1: List<PlayInEventEntity?> = emptyList(),
    var westernPlayInEventRound2: PlayInEventEntity? = null,
    var easternPlayInEventRound1: List<PlayInEventEntity?> = emptyList(),
    var easternPlayInEventRound2: PlayInEventEntity? = null,
    var westernRound1Series: List<PlayOffSeriesEntity?> = emptyList(),
    var easternRound1Series: List<PlayOffSeriesEntity?> = emptyList(),
    var westernRound2Series: List<PlayOffSeriesEntity?> = emptyList(),
    var easternRound2Series: List<PlayOffSeriesEntity?> = emptyList(),
    var westernConferenceFinal: PlayOffSeriesEntity? = null,
    var easternConferenceFinal: PlayOffSeriesEntity? = null,
    var final: PlayOffSeriesEntity? = null
)

data class PlayOffSeriesEntity(
    var team1: PostSeasonTeamEntity,
    var team2: PostSeasonTeamEntity,
    var team1Wins: Int,
    var team2Wins: Int,
    var events: List<EventEntity?>
)

data class PlayInEventEntity(
    var homeTeam: PostSeasonTeamEntity,
    var guestTeam: PostSeasonTeamEntity,
    var result: Result?
)

data class PostSeasonTeamEntity(
    var teamAbbr: String,
    var displayName: String,
    var logo: String,
    var location: String,
    var rank: Int,
    var seed: Int,
    var recordSummaryWin: Int,
    var recordSummaryLose: Int,
    var isSurviveToPlayOff: Boolean,
    var isSurvive: Boolean = true
)