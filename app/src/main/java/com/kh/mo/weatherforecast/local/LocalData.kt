package com.kh.mo.weatherforecast.local

import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Units
import kotlinx.coroutines.flow.Flow

interface LocalData {


    suspend fun getWeatherState(
        latitude: Double,
        longitude: Double
    ): Flow<WeatherEntity>

    suspend fun saveWeatherState(weatherState: WeatherEntity)
    suspend fun updateWeatherState(weatherState: WeatherEntity)
    suspend fun deleteWeatherState(weatherState: WeatherEntity)
    suspend fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(favoriteName: String)
    suspend fun getFavorite(favoriteName: String): Flow<FavoriteEntity?>



    fun setTempUnit(unit: Units)
    fun getTempUnit(): String

    fun setLat(lat: Float)
    fun setLon(lon: Float)

    fun getLat(): Float
    fun getLon(): Float

    fun setLanguage(language: Language)
    fun getLanguage(): String

    fun setWindSpeed(windSpeed: Units)
    fun getWindSpeed(): String

    fun setLocation(location: Location)
    fun getLocation(): String


    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)

    fun checkTypeOfSelectLocation(): String
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String)

    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)



    fun clearSharedPreferences()


    fun getCurrentDate(): String

    fun getAddressLocation(
        lat: Double, lon: Double,
        getLocationData: (
            nameOfCity: String,
            nameOfCountry: String
        ) -> Unit
    )

}