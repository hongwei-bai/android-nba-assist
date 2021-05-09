package com.hongwei.android_nba_assist.datasource.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object NbaTypeConverters {
    @TypeConverter
    fun fromStringToEventList(value: String?): List<Event>? {
        val listType: Type = object : TypeToken<List<Event>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromEventList(list: List<Event>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToTeamStandingList(value: String?): List<TeamStanding>? {
        val listType: Type = object : TypeToken<List<TeamStanding>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTeamStandingList(list: List<TeamStanding>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}