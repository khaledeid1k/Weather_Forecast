package com.kh.mo.weatherforecast.model.ui

import androidx.room.Entity
import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly

data class WeatherState(
    val lan: Double=0.0,
    val lon: Double=0.0,
    val nameOfCity :String="",
    val currentTime :String="",
    val temp :Double=0.0,
    val unit :String="",
    val tempDescription :String="",
    val icon :String="",
    val humidity: Int=0,
    val clouds: Int=0,
    val wind_speed: Double=0.0,
    val pressure: Int=0,
    val hourly: List<Hourly> = emptyList(),
    val daily: List<Daily> = emptyList()
    )

