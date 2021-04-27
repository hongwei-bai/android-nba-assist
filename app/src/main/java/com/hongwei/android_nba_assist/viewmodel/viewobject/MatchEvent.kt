package com.hongwei.android_nba_assist.viewmodel.viewobject

import android.graphics.drawable.Drawable
import com.hongwei.android_nba_assist.datasource.network.model.Event
import com.hongwei.android_nba_assist.repository.NbaTeamRepository
import com.hongwei.android_nba_assist.util.LocalDateTimeUtil
import java.util.*

data class MatchEvent(
    val opponentAbbrev: String,
    val opponentLogoUrl: String,
    val opponentLogoPlaceholder: Drawable,
    val isHome: Boolean,
    val location: String,
    var date: Calendar,
    val result: Result?
) {
    companion object {
        fun fromResponseModel(
            event: Event,
            nbaTeamRepository: NbaTeamRepository,
            teamShort: String = event.opponent.abbrev.toLowerCase(Locale.ROOT)
        ): MatchEvent = MatchEvent(
            opponentAbbrev = teamShort,
            opponentLogoUrl = nbaTeamRepository.getTeamLogoUrl(teamShort),
            opponentLogoPlaceholder = nbaTeamRepository.getTeamLogoPlaceholder(teamShort),
            isHome = event.opponent.home,
            location = event.opponent.location,
            date = LocalDateTimeUtil.unixTimeStampToCalendar(event.unixTimeStamp),
            result = Result.fromResponseResult(event.result)
        )
    }
}

data class Result(
    val winLossSymbol: String,
    val currentTeamScore: Int,
    val opponentTeamScore: Int
) {
    companion object {
        internal fun fromResponseResult(r: com.hongwei.android_nba_assist.datasource.network.model.Result?): Result? =
            r?.let {
                Result(
                    winLossSymbol = r.winLossSymbol,
                    currentTeamScore = r.currentTeamScore,
                    opponentTeamScore = r.opponentTeamScore
                )
            }

    }
}