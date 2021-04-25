package com.hongwei.android_nba_assistant.usecase

import android.graphics.drawable.Drawable
import java.util.*

data class MatchEvent(
    val opponentAbbrev: String,
    val opponentLogoUrl: String,
    val opponentLogoPlaceholder: Drawable,
    val isHome: Boolean,
    val location: String,
    var date: Calendar,
    val result: Result?
)

data class Result(
    val winLossSymbol: String,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
) {
    companion object {
        fun fromResponseResult(r: com.hongwei.android_nba_assistant.datasource.network.model.Result?): Result? =
            r?.let {
                Result(
                    winLossSymbol = r.winLossSymbol,
                    currentTeamScore = r.currentTeamScore,
                    opponentTeamScore = r.opponentTeamScore
                )
            }

    }
}