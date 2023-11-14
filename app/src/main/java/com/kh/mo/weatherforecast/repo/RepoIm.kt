package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.remot.ApiSate
import com.kh.mo.weatherforecast.remot.RemoteData
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RepoIm private constructor(
    private val localData: LocalData,
    private val remoteData: RemoteData
) : Repo {


    override suspend fun getCurrentUpdatedWeatherState(
        latitude: Double?,
        longitude: Double?
    ): Flow<ApiSate<Weather>> {

        return flow {

            emit(ApiSate.Loading)
            val currentUpdatedWeatherState =
                remoteData.getCurrentUpdatedWeatherState(latitude, longitude)
            if (currentUpdatedWeatherState.isSuccessful) {
                remoteData.getCurrentUpdatedWeatherState(latitude, longitude).body()
                    ?.let { emit(ApiSate.Success(it)) }
            } else {
                emit(ApiSate.Failure(currentUpdatedWeatherState.message()))
            }

        }.catch {
            emit(ApiSate.Failure(it.message!!))
        }

}


override suspend fun getSavedWeatherState(type: String, nameOfCity: String):
        Flow<ApiSate<CurrentWeather>> {
    return flow {
        emit(ApiSate.Loading)
        localData.getSavedWeatherState(type, nameOfCity)?.let {
            emit(ApiSate.Success(it))
        }?:   emit(ApiSate.Failure("No exit Data"))
    }.catch {
        emit(ApiSate.Failure(it.message.toString()))
    }

}

override suspend fun saveWeatherState(weatherState: CurrentWeather) {
    localData.saveWeatherState(weatherState)
}

override suspend fun updateWeatherState(weatherState: CurrentWeather) {
    localData.updateWeatherState(weatherState)
}


override fun setLat(lat: Double) {
    localData.setLat(lat)

}

override fun setLon(lon: Double) {
    localData.setLon(lon)
}

override fun getLat(): Double {
    return localData.getLat()
}

override fun getLon(): Double {
    return localData.getLon()
}

override fun getCityName(): String {
    return localData.getCityName()
}

override fun setCityName(nameOfCity: String) {
    localData.setCityName(nameOfCity)
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

override fun setWayOfSelectLocation(location: Location) {
    localData.setWayOfSelectLocation(location)
}

override fun getWayOfSelectLocation(): String {
    return localData.getWayOfSelectLocation()
}

override fun changeLanguageApp(language: String) {
    localData.changeLanguageApp(language)
}

override fun checkIsNotificationAvailable(): Boolean {
    return localData.checkIsNotificationAvailable()
}

override fun changeNotificationValue(isNotification: Boolean) {
    localData.changeNotificationValue(isNotification)
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
    return localData.getTempUnit()
}

override fun getCurrentDate(timestamp: Long): String {
    return localData.getCurrentDate(timestamp)
}

override fun getAddressLocation(
    lat: Double, lon: Double,
    getLocationData: (
        nameOfCity: String,
        nameOfCountry: String
    ) -> Unit
) {
    remoteData.getAddressLocation(lat, lon, getLanguage(), getLocationData)
}

override suspend fun getFavorites(): Flow<List<FavoriteEntity>> = localData.getFavorites()


override suspend fun saveFavorite(favorite: FavoriteEntity) {
    localData.saveFavorite(favorite)
}

override suspend fun deleteFavorite(nameOfCity: String) {
    localData.deleteFavorite(nameOfCity)
}

    override fun getAlerts(): Flow<List<AlertEntity>> {
        return localData.getAlerts()
    }

    override suspend fun saveAlert(alertEntity: AlertEntity) {
      localData.saveAlert(alertEntity)
    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {
        localData.deleteAlert(alertEntity)
    }


    override fun daysName(): List<String> {
    return localData.daysName()
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