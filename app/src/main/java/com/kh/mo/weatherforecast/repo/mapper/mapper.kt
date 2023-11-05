package com.kh.mo.weatherforecast.repo.mapper

import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.utils.convertUnixTimestampToDateTime

fun List<Hourly>?.convertToWeatherHoursData(): List<WeatherHourData>? {
    return this?.map {
        WeatherHourData(
            it.dt?.convertUnixTimestampToDateTime(),
            it.weather?.get(0)?.icon,
            it.temp
        )
    }
}

fun List<Daily>?.convertToWeatherWeekData(): List<WeatherWeekData>? {
    val dayNames = listOf( "Sat","Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
    return this?.drop(1)?.mapIndexed { index, daily ->
        WeatherWeekData(
            index.takeIf { index<this.size }?.let { dayNames[it] },
            daily.weather?.get(0)?.icon,
            daily.weather?.get(0)?.description,
            daily.temp?.day
        )
    }
}