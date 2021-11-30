package com.hami.sports_assist.data.room.nba

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hami.sports_assist.AppConfigurations.Room.API_VERSION

@Entity(tableName = "transactions")
data class NbaTransactionsEntity(
    @PrimaryKey
    val apiVersion: Int = API_VERSION,
    val dataVersion: Long = 0,
    val transactions: List<NbaTransaction> = emptyList()
)

data class NbaTransaction(
    val unixTimeStamp: Long,
    val description: String,
    val teamAbbr: String,
    val teamDisplayName: String,
    val teamLogo: String
)