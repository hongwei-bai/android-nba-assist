package com.hongwei.android_nba_assistant.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_schedule")
data class TeamScheduleEntity(
    @PrimaryKey
    val team: String = "",

    val dataVersion: Long = -1,

    val data: String = "{}"
)

