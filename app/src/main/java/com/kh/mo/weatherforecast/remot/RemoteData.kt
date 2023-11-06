package com.kh.mo.weatherforecast.remot

import com.kh.mo.weatherforecast.model.Weather
import retrofit2.http.Query

interface RemoteData {
    suspend fun getCurrentTemperature(
         latitude: Double,
        longitude: Double
    ): Weather?
}