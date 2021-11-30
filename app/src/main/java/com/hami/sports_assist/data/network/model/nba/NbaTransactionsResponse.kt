package com.hami.sports_assist.data.network.model.nba

data class NbaTransactionsResponse(
    val dataVersion: Long,
    val transactions: List<TransactionResponse>
)

data class TransactionResponse(
    val unixTimeStamp: Long,
    val description: String,
    val teamAbbr: String,
    val teamDisplayName: String,
    val teamLogo: String
)