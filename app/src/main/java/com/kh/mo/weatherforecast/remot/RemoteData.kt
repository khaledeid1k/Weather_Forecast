package com.kh.mo.weatherforecast.remot

import com.kh.mo.weatherforecast.model.Weather
import retrofit2.Response

interface RemoteData {
    suspend fun getCurrentUpdatedWeatherState(
         latitude: Double?,
        longitude: Double?
    ): Response<Weather>

    fun getAddressLocation(
        lat: Double, lon: Double, language:String,
        getLocationData: (
            nameOfCity: String,
            nameOfCountry: String
        ) -> Unit
    )
}