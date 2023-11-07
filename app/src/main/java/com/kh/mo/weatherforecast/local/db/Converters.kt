package com.kh.mo.weatherforecast.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun convertFromHourliesToGson(hourlies: List<Hourly?>): String {
        return gson.toJson(hourlies)
    }

    @TypeConverter
    fun convertFromGsonToHourlies(hourlies: String): List<Hourly?> {
        val listType = object : TypeToken<List<Hourly?>>() {}.type
        return gson.fromJson(hourlies, listType)
    }



    @TypeConverter
    fun convertFromDailiesToGson(dailies: List<Daily?>): String {
        return gson.toJson(dailies)
    }

    @TypeConverter
    fun convertFromGsonToDailies(dailies: String): List<Daily?> {
        val listType = object : TypeToken<List<Daily?>>() {}.type
        return gson.fromJson(dailies, listType)
    }
}