package com.kh.mo.weatherforecast.local

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kh.mo.weatherforecast.model.ui.WeatherState

interface LocalData {
    suspend fun getWeatherState(  latitude: Double,
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