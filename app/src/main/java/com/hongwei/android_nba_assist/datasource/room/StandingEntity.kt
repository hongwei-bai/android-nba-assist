package com.hongwei.android_nba_assist.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "standing")
data class StandingEntity(
    @PrimaryKey
    val dateDiff: Int,

    val timeStamp: Long,

    val dataVersion: Long = -1,

    val data: String = "{}"
)

