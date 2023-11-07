package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.model.ui.WeatherState
import com.kh.mo.weatherforecast.remot.RemoteData
import com.kh.mo.weatherforecast.repo.mapper.convertToWeatherWeekData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoIm private constructor(
    private val localData: LocalData,
    private val remoteData: RemoteData
) : Repo {

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


    override suspend fun getCurrentWeatherState(
        latitude: Double,
        longitude: Double
    ): WeatherState {
        val currentTemperature = remoteData.getCurrentWeatherState(latitude, longitude)

        if (currentTemperature.isSuccessful) {
            var nameOfCity=""
            getAddressLocation(latitude, longitude) { nameCity, _ ->
                nameOfCity=nameCity
            }

           val weatherState= currentTemperature.body()?.convertToWeatherWeekData(
               nameOfCity, getCurrentDate(), "metric"
           )
            localData.saveWeatherState(weatherState!!)

            return weatherState
        }

        return localData.getWeatherState(latitude, longitude)
    }

    override suspend fun getWeatherState(latitude: Double, longitude: Double): WeatherState {
        return localData.getWeatherState(latitude, longitude)
    }

    override suspend fun saveWeatherState(weatherState: WeatherState) {
        localData.saveWeatherState(weatherState)
    }

    override suspend fun updateWeatherState(weatherState: WeatherState) {
        localData.updateWeatherState(weatherState)
    }

    override suspend fun deleteWeatherState(weatherState: WeatherState) {
        localData.deleteWeatherState(weatherState)
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
}