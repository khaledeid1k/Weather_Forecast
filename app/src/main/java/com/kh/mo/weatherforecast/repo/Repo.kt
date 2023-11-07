package com.kh.mo.weatherforecast.repo

import android.content.Context
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.ui.WeatherState
import retrofit2.Response

interface Repo {
    suspend fun getCurrentWeatherState(
        latitude: Double,
        longitude: Double
    ): WeatherState

    suspend fun getWeatherState(   latitude: Double,
                                   longitude: Double): WeatherState
    suspend fun saveWeatherState(weatherState: WeatherState)
    suspend fun updateWeatherState(weatherState: WeatherState)
    suspend fun deleteWeatherState(weatherState: WeatherState)





    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)

    fun checkTypeOfSelectLocation(): String
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String)

    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)

    fun clearSharedPreferences()

    fun getCurrentDate(): String

    fun getAddressLocation(lat: Double, lon: Double,
                           getLocationData:(nameOfCity:String,
                                            nameOfCountry:String)->Unit
    )
}