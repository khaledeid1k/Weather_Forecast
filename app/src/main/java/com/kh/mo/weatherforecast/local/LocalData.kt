package com.kh.mo.weatherforecast.local

import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.ui.setting.Units
import kotlinx.coroutines.flow.Flow

interface LocalData {


    suspend fun getSavedWeatherState(
        type:String,nameOfCity:String ): CurrentWeather
    suspend fun saveWeatherState(weatherState: CurrentWeather)
    suspend fun updateWeatherState(weatherState: CurrentWeather)


    suspend fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(nameOfCity: String)



    fun setTempUnit(unit: Units)
    fun getTempUnit(): String

    fun setLat(lat: Double)
    fun setLon(lon: Double)

    fun getLat(): Double
    fun getLon(): Double

    fun getCityName(): String
    fun setCityName(nameOfCity:String)

    fun setLanguage(language: Language)
    fun getLanguage(): String

    fun setWindSpeed(windSpeed: Units)
    fun getWindSpeed(): String

    fun setLocation(location: Location)
    fun getLocation(): String


    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)



    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)



    fun clearSharedPreferences()


    fun getCurrentDate(timestamp:Long): String



    fun changeLanguageApp(language: String)

    fun daysName():List<String>

}