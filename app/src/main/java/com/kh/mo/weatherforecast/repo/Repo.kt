package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import com.kh.mo.weatherforecast.model.ui.Favorite
import com.kh.mo.weatherforecast.model.ui.WeatherState
import kotlinx.coroutines.flow.Flow

interface Repo {


    suspend fun getCurrentUpdatedWeatherState(
        latitude: Double,
        longitude: Double
    ):Flow<Weather>








    suspend fun getWeatherState(   latitude: Double,
                                   longitude: Double): Flow<WeatherState>
    suspend fun saveWeatherState(weatherEntity: WeatherEntity)
    suspend fun updateWeatherState(weatherEntity: WeatherEntity)
    suspend fun deleteWeatherState(weatherEntity: WeatherEntity)




    fun setLat(lat:Float)
    fun setLon(lon:Float)
    fun getLat():Double
    fun getLon():Double

    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)

    fun checkTypeOfSelectLocation(): String
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String)

    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)

    fun clearSharedPreferences()

    fun setUnit(unit: String)
    fun getUnit(): String

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