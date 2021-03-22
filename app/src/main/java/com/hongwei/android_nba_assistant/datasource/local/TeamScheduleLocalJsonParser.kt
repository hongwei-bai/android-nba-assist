package com.hongwei.android_nba_assistant.datasource.local

import android.content.Context
import com.hongwei.android_nba_assistant.constant.AppConfigurations.Stub.GS_SCHEDULE_JSON
import com.hongwei.android_nba_assistant.datasource.TeamScheduleService
import com.hongwei.android_nba_assistant.model.TeamSchedule
import com.hongwei.android_nba_assistant.util.LocalStorageUtils.getAssetJsonFile
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TeamScheduleLocalJsonParser @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) : TeamScheduleService {
    override suspend fun getTeamSchedule(team: String): TeamSchedule {
        val jsonString = getAssetJsonFile(context, GS_SCHEDULE_JSON)
        val jsonAdapter: JsonAdapter<TeamSchedule> = moshi.adapter(TeamSchedule::class.java)
        return jsonAdapter.fromJson(jsonString)!!
    }
}