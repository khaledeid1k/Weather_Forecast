package com.kh.mo.weatherforecast.remot

import com.kh.mo.weatherforecast.model.Weather
import retrofit2.Response
import retrofit2.http.Query

interface RemoteData {
    suspend fun getCurrentWeatherState(
         latitude: Double,
        longitude: Double
    ): Response<Weather>
}