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
import com.kh.mo.weatherforecast.local.db.sharedPref.SharedPreferencesWeather.unit
import com.kh.mo.weatherforecast.model.entity.FavoriteEntity
import com.kh.mo.weatherforecast.model.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class LocalDataImp private constructor(val context: Context) : LocalData {
    private val dataBase = WeatherDataBase.getWeatherDataBaseInstance(context).weatherDao()
    private val sharedPreferencesWeather = SharedPreferencesWeather.customPreference(context)
    override suspend fun getWeatherState(
        latitude: Double,
        longitude: Double
    ): Flow<WeatherEntity> {
        return dataBase.getWeatherState(latitude, longitude)
    }

    override suspend fun saveWeatherState(weatherState: WeatherEntity) {
        dataBase.saveWeatherState(weatherState)
    }

    override suspend fun updateWeatherState(weatherState: WeatherEntity) {
        dataBase.updateWeatherState(weatherState)
    }

    override suspend fun deleteWeatherState(weatherState: WeatherEntity) {
        dataBase.deleteWeatherState(weatherState)
    }


    override fun checkIsNotificationAvailable(): Boolean {
        return sharedPreferencesWeather.isNotificationAvailable
    }

    override fun changeNotificationValue(isNotification: Boolean) {
        sharedPreferencesWeather.isNotificationAvailable = isNotification

    }

    override fun checkTypeOfSelectLocation(): String {
        return sharedPreferencesWeather.wayForSelectLocation.toString()
    }

    override fun changeWayOfSelectLocationValue(wayOfSelectLocation: String) {
        sharedPreferencesWeather.wayForSelectLocation = wayOfSelectLocation

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

    override fun setUnit(unit: String) {
        sharedPreferencesWeather.unit=unit
    }

    override fun getUnit(): String {
        return sharedPreferencesWeather.unit.toString()
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
            var nameOfCity = addressData[0]
            if (addresses.size > 1) {
                nameOfCity = addressData[1]
            }
            val nameOfCountry = addressData[addressData.size - 1]
            getLocationData(nameOfCity, nameOfCountry)

        }
    }

    override suspend fun getFavorites(): Flow<List<FavoriteEntity> >{
        return dataBase.getFavorites()
    }

    override suspend fun saveFavorite(favorite: FavoriteEntity) {
        dataBase.saveFavorite(favorite)
    }

    override suspend fun deleteFavorite(favoriteName: String) {
        dataBase.deleteFavorite(favoriteName)
    }

    override suspend fun getFavorite(favoriteName: String):Flow < FavoriteEntity?> {
      return  dataBase.getFavorite(favoriteName)
    }

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