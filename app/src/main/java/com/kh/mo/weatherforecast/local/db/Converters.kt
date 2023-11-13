package com.kh.mo.weatherforecast.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun convertFromHourliesToGson(hourlies: List<WeatherHourData?>): String {
        return gson.toJson(hourlies)
    }

    @TypeConverter
    fun convertFromGsonToHourlies(hourlies: String): List<WeatherHourData?> {
        val listType = object : TypeToken<List<WeatherHourData?>>() {}.type
        return gson.fromJson(hourlies, listType)
    }



    @TypeConverter
    fun convertFromDailiesToGson(dailies: List<WeatherWeekData?>): String {
        return gson.toJson(dailies)
    }

    @TypeConverter
    fun convertFromGsonToDailies(dailies: String): List<WeatherWeekData?> {
        val listType = object : TypeToken<List<WeatherWeekData?>>() {}.type
        return gson.fromJson(dailies, listType)
    }
}