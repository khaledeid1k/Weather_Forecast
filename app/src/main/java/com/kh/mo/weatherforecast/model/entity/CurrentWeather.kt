package com.kh.mo.weatherforecast.model.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData

@Entity(tableName = "WeatherState_Table", primaryKeys = ["nameOfCity","type"])
data class CurrentWeather(
    val lan: Double,
    val lon: Double,
    @NonNull
    val nameOfCity :String,
    val nameOfCountry :String,
    val currentTime :String,
    val temp :Double,
    val unit :String,
    val tempDescription :String,
    val icon :String,
    val humidity: Int,
    val clouds: Int,
    val wind_speed: Double,
    val pressure: Int,
    val hourly: List<WeatherHourData>,
    val daily: List<WeatherWeekData>,
    @NonNull
    val type:String

    )

