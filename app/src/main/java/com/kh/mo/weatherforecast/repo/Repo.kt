package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Units
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


    suspend fun getFavorites(): Flow<List<FavoriteEntity>>
    suspend fun saveFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavorite(favoriteName: String)
    suspend fun getFavorite(favoriteName: String): Flow < FavoriteEntity?>



    fun setTempUnit(unit: Units)
    fun getTempUnit(): String

    fun setLat(lat: Float)
    fun setLon(lon: Float)

    fun getLat():Double
    fun getLon():Double

    fun setLanguage(language: Language)
    fun getLanguage(): String

    fun setWindSpeed(windSpeed: Units)
    fun getWindSpeed(): String

    fun setLocation(location: Location)
    fun getLocation(): String
}