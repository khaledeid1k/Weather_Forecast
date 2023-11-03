package com.kh.mo.weatherforecast.model

data class Hourly(
    val clouds: Int?,
    val dew_point: Double?,
    val dt: Int?,
    val feels_like: Double?,
    val humidity: Int?,
    val pop: Int?,
    val pressure: Int?,
    val temp: Double?,
    val uvi: Double?,
    val visibility: Int?,
    val weather: List<WeatherX>?,
    val wind_deg: Int?,
    val wind_gust: Double?,
    val wind_speed: Double?
)