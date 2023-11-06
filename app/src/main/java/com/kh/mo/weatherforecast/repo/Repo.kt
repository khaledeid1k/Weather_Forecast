package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.model.Weather

interface Repo {
    suspend fun getCurrentTemperature(
        latitude: Double,
        longitude: Double
    ) : Weather?





    fun checkIsNotificationAvailable(): Boolean
    fun changeNotificationValue(isNotification: Boolean)

    fun checkTypeOfSelectLocation(): String
    fun changeWayOfSelectLocationValue(wayOfSelectLocation: String)

    fun checkIsFirstTimeOpenApp(): Boolean
    fun changeValueOfFirstTimeOpenApp(isFirstTime: Boolean)

    fun clearSharedPreferences()

    fun getCurrentDate(): String
}