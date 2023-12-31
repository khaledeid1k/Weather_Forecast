package com.kh.mo.weatherforecast.local

import android.annotation.SuppressLint
import android.content.Context
import com.kh.mo.weatherforecast.R
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.model.entity.AlertEntity
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import com.kh.mo.weatherforecast.utils.*
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class LocalDataImp private constructor(val context: Context) : LocalData {
    private val dataBase = WeatherDataBase.getWeatherDataBaseInstance(context).weatherDao()
    private val sharedPreferencesWeather =    SharedPreferencesWeather(context).customPreference()


    //region room
    override suspend fun getSavedWeatherState(type:String,nameOfCity:String
    ): CurrentWeather?{
        return dataBase.getSavedWeatherState(type,nameOfCity)
    }

    override suspend fun saveWeatherState(weatherState: CurrentWeather) {
        dataBase.saveWeatherState(weatherState)
    }

    override suspend fun updateWeatherState(weatherState: CurrentWeather) {
        dataBase.updateWeatherState(weatherState)
    }



    override suspend fun getFavorites(): Flow<List<FavoriteEntity>> {
        return dataBase.getFavorites()
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity) {
        dataBase.saveFavorite(favorite)
    }

    override suspend fun deleteFavorite(nameOfCity: String) {
        dataBase.deleteFavorite(nameOfCity)
    }

    override fun getAlerts(): Flow<List<AlertEntity>> {
        return dataBase.getAlerts()
    }

    override suspend fun saveAlert(alertEntity: AlertEntity) {
        dataBase.saveAlert(alertEntity)
    }

    override suspend fun deleteAlert(alertEntity: AlertEntity) {
        dataBase.deleteAlert(alertEntity)
    }


    //endregion

    //region shared Preferences
    override fun checkIsNotificationAvailable(): Boolean {
        return sharedPreferencesWeather.isNotificationAvailable
    }

    override fun changeNotificationValue(isNotification: Boolean) {
        sharedPreferencesWeather.isNotificationAvailable = isNotification

    }



    override fun checkIsFirstTimeOpenApp(): Boolean {
        return sharedPreferencesWeather.isFirstTimeOpenApp
    }

    override fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        sharedPreferencesWeather.isFirstTimeOpenApp = isFirstTime
    }

    override fun clearSharedPreferences() {
        sharedPreferencesWeather.clearValues()
    }

    override fun setTempUnit(unit: Units) {
        sharedPreferencesWeather.tempUnit = unit.nameOfUnit
    }

    override fun getTempUnit(): String {

        return sharedPreferencesWeather.tempUnit.toString()
    }

    override fun setLat(lat: Double) {
        sharedPreferencesWeather.lat = lat.toString()
    }

    override fun setLon(lon: Double) {
        sharedPreferencesWeather.lon = lon.toString()
    }

    override fun getLat(): Double {
        return sharedPreferencesWeather.lat!!.toDouble()
    }

    override fun getLon(): Double {
        return sharedPreferencesWeather.lon!!.toDouble()
    }

    override fun getCityName(): String {
        return sharedPreferencesWeather.nameOfCity.toString()
    }

    override fun setCityName(nameOfCity: String) {
        sharedPreferencesWeather.nameOfCity = nameOfCity
    }

    override fun setLanguage(language: Language) {
        sharedPreferencesWeather.language = language.name
    }

    override fun getLanguage(): String = sharedPreferencesWeather.language.toString()

    override fun setWindSpeed(windSpeed: Units) {
        sharedPreferencesWeather.windSpeed=windSpeed.windSpeed
    }

    override fun getWindSpeed(): String = sharedPreferencesWeather.windSpeed.toString()

    override fun setWayOfSelectLocation(location: Location) {
        sharedPreferencesWeather.wayOfSelectLocation=location.name
    }

    override fun getWayOfSelectLocation(): String =sharedPreferencesWeather.wayOfSelectLocation.toString()

    //endregion

    // region util
    override fun getCurrentDate(timestamp:Long): String {
        val date = Date(timestamp * 1000)


        return SimpleDateFormat("dd MMM, yyyy", Locale(getLanguage())).format(date)

    }



    override fun changeLanguageApp(language: String) {
        val  myLocal = Locale(language)
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.locale = myLocal
        resources.updateConfiguration(configuration, displayMetrics)
    }

    //endregion

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: LocalDataImp? = null
        fun getLocalDataImpInstance(context: Context): LocalDataImp {
            return instance ?: synchronized(this) {
                val instanceHolder = LocalDataImp(context)
                instance = instanceHolder
                instanceHolder

            }
        }
    }
override  fun daysName():List<String>{
    return listOf(context.getString(R.string.sat),
        context.getString(R.string.sun), context.getString(R.string.mon),
        context.getString(R.string.tue), context.getString(R.string.wen),
        context.getString(R.string.thu), context.getString(R.string.fri))
}
}