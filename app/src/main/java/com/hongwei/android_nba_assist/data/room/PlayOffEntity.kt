package com.hongwei.android_nba_assist.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.hongwei.android_nba_assist.constant.AppConfigurations.Room.API_VERSION

@Entity(tableName = "playoff")
data class PlayOffEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("time_stamp")
    val timeStamp: Long,
    @SerializedName("data_version")
    val dataVersion: Long = -1,
    @SerializedName("season_ongoing")
    val seasonOngoing: Boolean,
    @SerializedName("play_in_ongoing")
    val playInOngoing: Boolean,
    @SerializedName("playoff_ongoing")
    val playOffOngoing: Boolean,
    @SerializedName("play_in")
    val playIn: PlayInEntity,
    @SerializedName("playoff_sub")
    val playOff: PlayOffSubEntity
)

data class PlayInEntity(
    val western: PlayInConferenceEntity,
    val eastern: PlayInConferenceEntity
)

data class PlayOffSubEntity(
    val western: PlayOffConferenceEntity,
    val eastern: PlayOffConferenceEntity,
    val grandFinal: PlayOffGrandFinalSeriesEntity
)

data class PlayInConferenceEntity(
    val winnerOf78: String,
    val loserOf78: String,
    val winnerOf910: String,
    val loserOf910: String,
    val lastWinner: String
)

data class PlayOffConferenceEntity(
    val round1: PlayOffRound1Entity,
    val round2: PlayOffRound2Entity,
    val conferenceFinal: PlayOffSeriesEntity
)

data class PlayOffRound1Entity(
    val series18: PlayOffSeriesRound1Entity,
    val series45: PlayOffSeriesRound1Entity,
    val series36: PlayOffSeriesRound1Entity,
    val series27: PlayOffSeriesRound1Entity
)

data class PlayOffRound2Entity(
    val seriesUpper: PlayOffSeriesEntity,
    val seriesLower: PlayOffSeriesEntity
)

data class PlayOffSeriesRound1Entity(
    val scoreHighRank: Int,
    val scoreLowRank: Int,
    val winner: String
)

data class PlayOffSeriesEntity(
    val teamFromUpper: String,
    val teamFromLower: String,
    val scoreUpperWinner: Int,
    val scoreLowerWinner: Int,
    val winner: String
)

data class PlayOffGrandFinalSeriesEntity(
    val teamFromWestern: String,
    val teamFromEastern: String,
    val scoreWesternWinner: Int,
    val scoreEasternWinner: Int,
    val winner: String
)