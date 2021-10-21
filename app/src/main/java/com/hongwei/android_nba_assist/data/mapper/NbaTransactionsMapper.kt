package com.hongwei.android_nba_assist.data.mapper

import com.hongwei.android_nba_assist.data.network.model.NbaTransactionsResponse
import com.hongwei.android_nba_assist.data.network.model.TransactionResponse
import com.hongwei.android_nba_assist.data.room.NbaTransaction
import com.hongwei.android_nba_assist.data.room.NbaTransactionsEntity

object NbaTransactionsMapper {
    fun NbaTransactionsResponse.map(): NbaTransactionsEntity = NbaTransactionsEntity(
        dataVersion = dataVersion,
        transactions = transactions.map { it.map() }
    )

    private fun TransactionResponse.map(): NbaTransaction = NbaTransaction(
        unixTimeStamp = unixTimeStamp,
        description = description,
        teamAbbr = teamAbbr,
        teamDisplayName = teamDisplayName,
        teamLogo = teamLogo
    )
}