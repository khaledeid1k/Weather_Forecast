package com.kh.mo.weatherforecast.remot

import com.kh.mo.weatherforecast.model.Weather
import retrofit2.Response

interface RemoteData {
    suspend fun getCurrentUpdatedWeatherState(
         latitude: Double,
        longitude: Double
    ): Response<Weather>
}