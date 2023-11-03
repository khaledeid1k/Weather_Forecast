package com.kh.mo.weatherforecast.local

interface LocalData {
    suspend fun getWeatherOfCurrentDay()
}