package com.kh.mo.weatherforecast.local

import android.content.Context
import androidx.room.*
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

interface LocalData {
    suspend fun getWeatherState(  latitude: Double,
                                  longitude: Double): Flow<WeatherEntity>
    suspend fun saveWeatherState(weatherState: WeatherEntity)
    suspend fun updateWeatherState(weatherState: WeatherEntity)
    suspend fun deleteWeatherState(weatherState: WeatherEntity)

    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)

    fun checkTypeOfSelectLocation(): String
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String)

    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)

    fun clearSharedPreferences()

    fun setUnit(unit:String)
    fun getUnit():String

    fun getCurrentDate(): String

    fun getAddressLocation(lat: Double, lon: Double,
                           getLocationData:(nameOfCity:String,
                                            nameOfCountry:String)->Unit
    )



    suspend fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)

    suspend fun deleteFavorite(favoriteName: String)

    suspend fun getFavorite(favoriteName: String): Flow < FavoriteEntity?>
}