package com.hami.sports_assist.data.mapper

import com.hami.sports_assist.data.network.model.nba.NbaTransactionsResponse
import com.hami.sports_assist.data.network.model.nba.TransactionResponse
import com.hami.sports_assist.data.room.nba.NbaTransaction
import com.hami.sports_assist.data.room.nba.NbaTransactionsEntity

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