package com.kh.mo.weatherforecast.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.clearValues
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.isFirstTimeOpenApp
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.isNotificationAvailable
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.wayForSelectLocation
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import com.kh.mo.weatherforecast.model.ui.WeatherState
import java.text.SimpleDateFormat
import java.util.*

class LocalDataImp private constructor(val context: Context) :LocalData {
    private val dataBase= WeatherDataBase.getWeatherDataBaseInstance(context).weatherDao()
    private val sharedPreferencesWeather= SharedPreferencesWeather.customPreference(context,)
    override suspend fun getWeatherState(  latitude: Double,
                                           longitude: Double): WeatherState {
       return dataBase.getWeatherState(latitude,longitude)
    }

    override suspend fun saveWeatherState(weatherState: WeatherState) {
         dataBase.saveWeatherState(weatherState)
    }

    override suspend fun updateWeatherState(weatherState: WeatherState) {
         dataBase.updateWeatherState(weatherState)
    }

    override suspend fun deleteWeatherState(weatherState: WeatherState) {
        dataBase.deleteWeatherState(weatherState)
    }


    override fun checkIsNotificationAvailable(): Boolean {
       return sharedPreferencesWeather.isNotificationAvailable
    }

    override fun changeNotificationValue(isNotification: Boolean) {
        sharedPreferencesWeather.isNotificationAvailable=isNotification

    }

    override fun checkTypeOfSelectLocation(): String {
        return sharedPreferencesWeather.wayForSelectLocation.toString()
    }

    override fun changeWayOfSelectLocationValue(wayOfSelectLocation: String) {
        sharedPreferencesWeather.wayForSelectLocation=wayOfSelectLocation

    }

    override fun checkIsFirstTimeOpenApp(): Boolean {
        return sharedPreferencesWeather.isFirstTimeOpenApp
    }

    override fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean) {
        sharedPreferencesWeather.isFirstTimeOpenApp=isFirstTime
    }

    override fun clearSharedPreferences() {
        sharedPreferencesWeather.clearValues()
    }

    override fun getCurrentDate(): String {
        return SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(Date())
    }

    override fun getAddressLocation(
        lat: Double,
        lon: Double,
        getLocationData: (nameOfCity: String, nameOfCountry: String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(
            lat, lon, 1
        )

        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            val fullAddress = address.getAddressLine(0)
            val addressData = fullAddress.split(",")
            val nameOfCity = addressData[1]
            val nameOfCountry = addressData[addressData.size-1]
            getLocationData(nameOfCity,nameOfCountry)

        }
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private  var instance: LocalDataImp?=null
        fun getLocalDataImpInstance( context: Context): LocalDataImp {
            return instance ?: synchronized(this){
                val instanceHolder=LocalDataImp(context)
                instance =instanceHolder
                instanceHolder

            }
        }
    }

}