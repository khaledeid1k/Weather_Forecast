package com.kh.mo.weatherforecast.model.entity

import androidx.room.Entity
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly

@Entity(tableName = "WeatherState_Table", primaryKeys = ["lan", "lon"])
data class WeatherEntity(
    val lan: Double,
    val lon: Double,
    val nameOfCity :String,
    val currentTime :String,
    val temp :Double,
    val unit :String,
    val tempDescription :String,
    val icon :String,
    val humidity: Int,
    val clouds: Int,
    val wind_speed: Double,
    val pressure: Int,
    val hourly: List<Hourly>,
    val daily: List<Daily>

    )
