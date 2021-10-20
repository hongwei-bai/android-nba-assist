package com.hongwei.android_nba_assist.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object NbaTypeConverters {
    @TypeConverter
    fun fromStringToTeamEventList(value: String?): List<TeamEvent>? {
        val listType: Type = object : TypeToken<List<TeamEvent>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTeamEventList(list: List<TeamEvent>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToTeam(value: String?): Team? {
        val type: Type = object : TypeToken<Team?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromTeam(obj: Team?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToConferenceStanding(value: String?): ConferenceStanding? {
        val type: Type = object : TypeToken<ConferenceStanding?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromConferenceStanding(obj: ConferenceStanding?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToPlayInEventEntity(value: String?): PlayInEventEntity? {
        val type: Type = object : TypeToken<PlayInEventEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPlayInEventEntity(obj: PlayInEventEntity?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToPlayInEventListEntity(value: String?): List<PlayInEventEntity?> {
        val type: Type = object : TypeToken<List<PlayInEventEntity?>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPlayInEventListEntity(obj: List<PlayInEventEntity?>): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToPlayOffSeriesEntity(value: String?): PlayOffSeriesEntity? {
        val type: Type = object : TypeToken<PlayOffSeriesEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPlayOffSeriesEntity(obj: PlayOffSeriesEntity?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToPlayOffSeriesListEntity(value: String?): List<PlayOffSeriesEntity?> {
        val type: Type = object : TypeToken<List<PlayOffSeriesEntity?>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPlayOffSeriesListEntity(obj: List<PlayOffSeriesEntity?>): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToPostSeasonTeamEntity(value: String?): PostSeasonTeamEntity? {
        val type: Type = object : TypeToken<PostSeasonTeamEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPostSeasonTeamEntity(obj: PostSeasonTeamEntity?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromStringToResultEntity(value: String?): Result? {
        val type: Type = object : TypeToken<Result?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromResultEntity(obj: Result?): String? {
        val gson = Gson()
        return gson.toJson(obj)
    }
}