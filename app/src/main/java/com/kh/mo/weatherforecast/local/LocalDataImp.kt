package com.kh.mo.weatherforecast.local

import android.content.Context
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather.clearValues
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather.isFirstTimeOpenApp
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather.isNotificationAvailable
import com.kh.mo.weatherforecast.local.db.SharedPreferencesWeather.wayForSelectLocation
import com.kh.mo.weatherforecast.local.db.WeatherDataBase
import java.text.SimpleDateFormat
import java.util.*

class LocalDataImp private constructor(context: Context) :LocalData {
    private val dataBase= WeatherDataBase.getWeatherDataBaseInstance(context)?.weatherDao()
    private val sharedPreferencesWeather= SharedPreferencesWeather.customPreference(context,)

    override suspend fun getWeatherOfCurrentDay() {
     //   dataBase?.getWeatherOfCurrentDay()
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

    companion object{
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