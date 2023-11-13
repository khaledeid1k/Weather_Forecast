package com.kh.mo.weatherforecast.data.remote

import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.RemoteData
import retrofit2.Response

class FakeRemoteDataSource : RemoteData {
    var currentWeather: Weather? = null

    override suspend fun getCurrentUpdatedWeatherState(
        latitude: Double?,
        longitude: Double?
    ): Response<Weather> {
       return if(currentWeather!=null){
           Response.success(currentWeather)
        }else{
            Response.error(400,null)
        }

    }

    override fun getAddressLocation(
        lat: Double,
        lon: Double,
        language: String,
        getLocationData: (nameOfCity: String, nameOfCountry: String) -> Unit
    ) {

    }

}