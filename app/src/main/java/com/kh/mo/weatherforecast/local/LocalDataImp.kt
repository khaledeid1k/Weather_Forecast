package com.kh.mo.weatherforecast.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.clearValues
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.isFirstTimeOpenApp
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.isNotificationAvailable
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.language
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.lat
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.location
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.lon
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.nameOfCity
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.tempUnit
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.windSpeed
import com.kh.mo.weatherforecast.model.entity.CurrentWeather
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.ui.setting.Language
import com.kh.mo.weatherforecast.ui.setting.Units
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class LocalDataImp private constructor(val context: Context) : LocalData {
    private val dataBase = WeatherDataBase.getWeatherDataBaseInstance(context).weatherDao()
    private val sharedPreferencesWeather = SharedPreferencesWeather.customPreference(context)


    //region room
    override suspend fun getSavedWeatherState(type:String,nameOfCity:String
    ): CurrentWeather{
        return dataBase.getSavedWeatherState(type,nameOfCity)
    }

    override suspend fun saveWeatherState(weatherState: CurrentWeather) {
        dataBase.saveWeatherState(weatherState)
    }

    override suspend fun updateWeatherState(weatherState: CurrentWeather) {
        dataBase.updateWeatherState(weatherState)
    }

    override suspend fun deleteWeatherState(weatherState: CurrentWeather) {
        dataBase.deleteWeatherState(weatherState)
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

    override fun setLocation(location: Location) {
        sharedPreferencesWeather.location=location.name
    }

    override fun getLocation(): String =sharedPreferencesWeather.location.toString()

    //endregion

    // region util
    override fun getCurrentDate(timestamp:Long): String {
        val date = Date(timestamp * 1000)

        return SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(date)

    }

    override fun getAddressLocation(
        lat: Double,
        lon: Double,
        getLocationData: (nameOfCity: String, nameOfCountry: String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {


        val addresses = geocoder.getFromLocation(
            lat, lon, 1
        ,)

        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            val fullAddress = address.getAddressLine(0)
            val addressData = fullAddress.split(",")
            var nameOfCity = addressData[0]
            if (addresses.size > 1) {
                nameOfCity = addressData[1]
            }
            val nameOfCountry = addressData[addressData.size - 1]
            getLocationData(nameOfCity, nameOfCountry)

        }
    }catch (e:Exception){}
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

}