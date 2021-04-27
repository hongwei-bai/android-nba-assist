package com.hongwei.android_nba_assist.util

import android.util.Log
import com.hongwei.android_nba_assist.constant.AppConfigurations
import com.hongwei.android_nba_assist.viewmodel.viewobject.MatchEvent
import java.util.*

object DebugUtil {
    fun debugInt(value: Int): Int {
        Log.d("bbbb", "value: $value")
        return value
    }

    fun debugSetData(data: List<MatchEvent>, eventByPositionHashMap: HashMap<Int, MatchEvent>) {
        if (AppConfigurations.Debug.DEBUG_CALENDAR) {
            data.forEachIndexed { index, matchEvent ->
                Log.d("bbbb", "setData[$index] ${LocalDateTimeUtil.debugDateTime(matchEvent.date)} ${matchEvent.opponentAbbrev}")
            }
            eventByPositionHashMap.forEach { t, u ->
                Log.d("bbbb", "setData trigger eventByPositionHashMap[$t] -> $u")
            }
        }
    }

    fun debugInitBlock(dayIdList: MutableList<Long>) {
        if (AppConfigurations.Debug.DEBUG_CALENDAR) {
            dayIdList.forEachIndexed { i, l ->
                Log.d(
                    "bbbb", "init dayIdList[$i] dayId: $l, " +
                            "display: ${LocalDateTimeUtil.debugDateTime(Calendar.getInstance().apply { timeInMillis = l })}"
                )
            }
        }
    }
}