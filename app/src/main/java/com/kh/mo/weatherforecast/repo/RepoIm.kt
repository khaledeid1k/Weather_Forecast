package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.remot.RemoteData
import com.kh.mo.weatherforecast.repo.mapper.convertWeatherToFavoriteEntity
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepoIm private constructor(
    private val localData: LocalData,
    private val remoteData: RemoteData
) : Repo {


    override suspend fun getCurrentUpdatedWeatherState(
        latitude: Double,
        longitude: Double
    ): Flow<Weather> {
//        val currentTemperature = remoteData.getCurrentWeatherState(latitude, longitude)
//
//        if (currentTemperature.isSuccessful) {
//            var nameOfCity=""
//            getAddressLocation(latitude, longitude) { nameCity, _ ->
//                nameOfCity=nameCity
//            }
//
//           val weatherState= currentTemperature.body()?.convertToWeatherWeekData(
//               nameOfCity, getCurrentDate(), "metric"
//           )
//            localData.saveWeatherState(weatherState!!)
//
//            return weatherState
//        }
//
//        return localData.getWeatherState(latitude, longitude)



        return flow {
            remoteData.
            getCurrentUpdatedWeatherState(latitude, longitude)
                .body()?.let {
                emit(
                    it
                )
            }

        }
    }

    override suspend fun getWeatherState(latitude: Double, longitude: Double):
            Flow<WeatherState> {
        return localData.getWeatherState(latitude, longitude).map {
            it.convertWeatherToFavoriteEntity()
        }
    }

    override suspend fun saveWeatherState(weatherEntity: WeatherEntity) {
        localData.saveWeatherState(weatherEntity)
    }

    override suspend fun updateWeatherState(weatherEntity: WeatherEntity) {
        localData.updateWeatherState(weatherEntity)
    }

    override suspend fun deleteWeatherState(weatherEntity: WeatherEntity) {
        localData.deleteWeatherState(weatherEntity)
    }

    override fun setLat(lat: Float) {
        localData.setLat(lat)

    }

    override fun setLon(lon: Float) {
        localData.setLon(lon)
    }

    override fun getLat(): Double {
        return localData.getLat().toDouble()
    }

    override fun getLon(): Double {
        return localData.getLon().toDouble()
    }

    override fun setLanguage(language: Language) {
        localData.setLanguage(language)
    }

    override fun getLanguage(): String {
       return localData.getLanguage()
    }

    override fun setWindSpeed(windSpeed: Units) {
        localData.setWindSpeed(windSpeed)
    }

    override fun getWindSpeed(): String {
        return localData.getWindSpeed()
    }

    override fun setLocation(location: Location) {
        localData.setLocation(location)
    }

    override fun getLocation(): String {
     return   localData.getLocation()
    }

    override fun checkIsNotificationAvailable(): Boolean {
        return localData.checkIsNotificationAvailable()
    }

    override fun changeNotificationValue(isNotification: Boolean) {
        localData.changeNotificationValue(isNotification)
    }

    override fun checkTypeOfSelectLocation(): String {
        return localData.checkTypeOfSelectLocation()
    }

    override fun changeWayOfSelectLocationValue(wayOfSelectLocation: String) {
        localData.changeWayOfSelectLocationValue(wayOfSelectLocation)
    }

    override fun checkIsFirstTimeOpenApp(): Boolean {
        return localData.checkIsFirstTimeOpenApp()
    }

    override fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        localData.changeValueOfFirstTimeOpenApp(isFirstTime)
    }

    override fun clearSharedPreferences() {
        localData.clearSharedPreferences()
    }

    override fun setTempUnit(unit: Units) {
        localData.setTempUnit(unit)
    }

    override fun getTempUnit(): String {
     return   localData.getTempUnit()
    }

    override fun getCurrentDate(): String {
        return localData.getCurrentDate()
    }

    override fun getAddressLocation(
        lat: Double, lon: Double,
        getLocationData: (
            nameOfCity: String,
            nameOfCountry: String
        ) -> Unit
    ) {
        localData.getAddressLocation(lat, lon, getLocationData)
    }

    override suspend fun getFavorites(): Flow<List<FavoriteEntity>> = localData.getFavorites()


    override suspend fun saveFavorite(favorite: FavoriteEntity) {
        localData.saveFavorite(favorite)
    }

    override suspend fun deleteFavorite(favoriteName: String) {
        localData.deleteFavorite(favoriteName)
    }

    override suspend fun getFavorite(favoriteName: String): Flow<FavoriteEntity?> {
        return localData.getFavorite(favoriteName)
    }


    companion object {
        @Volatile
        private var instance: RepoIm? = null
        fun getRepoImInstance(localData: LocalData, remoteData: RemoteData): RepoIm {
            return instance ?: synchronized(this) {
                val instanceHolder = RepoIm(localData, remoteData)
                instance = instanceHolder
                instanceHolder

            }
        }
    }
}