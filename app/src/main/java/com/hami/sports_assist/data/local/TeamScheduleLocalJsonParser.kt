package com.hami.sports_assist.data.local

import android.content.Context
import com.hami.sports_assist.AppConfigurations.Mock.TEAM_SCHEDULE_JSON
import com.hami.sports_assist.data.network.model.nba.TeamScheduleResponse
import com.hami.sports_assist.util.LocalStorageUtils.getAssetJsonFile
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TeamScheduleLocalJsonParser @Inject constructor(
        @ApplicationContext private val context: Context,
        private val moshi: Moshi
) {
    suspend fun getTeamSchedule(team: String): TeamScheduleResponse {
        val jsonString = getAssetJsonFile(context, TEAM_SCHEDULE_JSON.replace("{team}", team))
        val jsonAdapter: JsonAdapter<TeamScheduleResponse> = moshi.adapter(TeamScheduleResponse::class.java)
        return jsonAdapter.fromJson(jsonString)!!
    }
}