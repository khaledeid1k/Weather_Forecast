package com.kh.mo.weatherforecast.data.local

import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource(
    private val currentWeather: CurrentWeather,
    private val favorites: MutableList<FavoriteEntity>,
    sharedPreferences: SharedPreferencesWeather

) : LocalData {


    private val shared = sharedPreferences.customPreference()
    override suspend fun getSavedWeatherState(type: String, nameOfCity: String): CurrentWeather {
        return currentWeather
    }

    override suspend fun saveWeatherState(weatherState: CurrentWeather) {
        shared.nameOfCity
    }

    override suspend fun updateWeatherState(weatherState: CurrentWeather) {

    }

    override suspend fun getFavorites(): Flow<List<FavoriteEntity>> {
        return flow { emit(favorites) }
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity) {
        favorites.add(favorite)
    }

    override suspend fun deleteFavorite(nameOfCity: String) {
        favorites.removeIf { it.nameOfCity == nameOfCity }
    }

    override fun getAlerts(): Flow<List<AlertEntity>> {
     return   flow {
            emit(emptyList())
        }
    }

    override suspend fun saveAlert(alertEntity: AlertEntity) {

    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {

    }

    override fun setTempUnit(unit: Units) {
        shared.tempUnit = unit.nameOfUnit
    }

    override fun getTempUnit(): String {
        return shared.tempUnit.toString()
    }

    override fun setLat(lat: Double) {
        shared.lat = lat.toString()
    }

    override fun setLon(lon: Double) {
        shared.lon = lon.toString()
    }

    override fun getLat(): Double {
        return shared.lat!!.toDouble()
    }

    override fun getLon(): Double {
        return shared.lon!!.toDouble()
    }

    override fun getCityName(): String {
        return shared.nameOfCity.toString()
    }

    override fun setCityName(nameOfCity: String) {
        shared.nameOfCity = nameOfCity
    }

    override fun setLanguage(language: Language) {
        shared.language = language.name
    }

    override fun getLanguage(): String {
        return shared.language.toString()
    }

    override fun setWindSpeed(windSpeed: Units) {
        shared.wayOfSelectLocation = windSpeed.windSpeed
    }

    override fun getWindSpeed(): String {
        return shared.windSpeed.toString()
    }

    override fun setWayOfSelectLocation(location: Location) {
        shared.wayOfSelectLocation = location.name
    }

    override fun getWayOfSelectLocation(): String {
        return shared.wayOfSelectLocation.toString()
    }

    override fun checkIsNotificationAvailable(): Boolean {
        return shared.isNotificationAvailable
    }

    override fun changeNotificationValue(isNotification: Boolean) {
        shared.isNotificationAvailable = isNotification
    }

    override fun checkIsFirstTimeOpenApp(): Boolean {
        return shared.isFirstTimeOpenApp
    }

    override fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        shared.isFirstTimeOpenApp = isFirstTime
    }

    override fun clearSharedPreferences() {
        shared.clearValues
    }

    override fun getCurrentDate(timestamp: Long): String {
        return ""
    }

    override fun changeLanguageApp(language: String) {
        shared.language = language
    }

    override fun daysName(): List<String> {
        return emptyList()
    }
}