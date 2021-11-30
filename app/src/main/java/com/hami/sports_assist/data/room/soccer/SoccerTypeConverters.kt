package com.hami.sports_assist.data.room.soccer

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object SoccerTypeConverters {
    @TypeConverter
    fun fromStringToSoccerTeamEventList(value: String?): List<SoccerTeamEvent>? {
        val listType: Type = object : TypeToken<List<SoccerTeamEvent>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSoccerTeamEventList(list: List<SoccerTeamEvent>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}