package com.kh.mo.weatherforecast.remot.service

import com.kh.mo.weatherforecast.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ):Response<Weather>
}