package com.kh.mo.weatherforecast.repo.mapper

import com.kh.mo.weatherforecast.model.Daily
import com.kh.mo.weatherforecast.model.Hourly
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.ui.WeatherHourData
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.model.ui.WeatherWeekData
import com.kh.mo.weatherforecast.utils.convertUnixTimestampToDateTime

fun List<Hourly>.convertToWeatherHoursData(): List<WeatherHourData> {
    return this.map {
        WeatherHourData(
            it.dt.convertUnixTimestampToDateTime(),
            it.weather[0].icon,
            it.temp
        )
    }
}

fun List<Daily>.convertToWeatherWeekData(): List<WeatherWeekData> {
    val dayNames = listOf( "Sat","Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
    return this.drop(1).mapIndexed { index, daily ->
        WeatherWeekData(
            index.takeIf { index<this.size }.let { dayNames[it!!] },
            daily.weather[0].icon,
            daily.weather[0].description,
            daily.temp.day
        )
    }
}

fun Weather.convertToWeatherWeekData(nameOfCountry:String,currentTime:String,
unit :String): WeatherState {
    return this.let {
        WeatherState(
            it.lat,
            it.lon,
            nameOfCountry,
            currentTime,
            it.current.temp,
            unit,
            it.current.weather[0].description,
            it.current.weather[0].icon,
            it.current.humidity,
            it.current.clouds,
            it.current.wind_speed,
            it.current.pressure,
            hourly,
            daily
        )


    }
}



