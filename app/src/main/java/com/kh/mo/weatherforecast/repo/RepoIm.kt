package com.kh.mo.weatherforecast.repo

import com.kh.mo.weatherforecast.local.LocalData
import com.kh.mo.weatherforecast.model.Weather
import com.kh.mo.weatherforecast.remot.RemoteData
import java.text.SimpleDateFormat
import java.util.*

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


    override suspend fun getCurrentTemperature(
        latitude: Double,
        longitude: Double
    ): Weather? {
        return remoteData.getCurrentTemperature(latitude, longitude)
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

    override  fun getCurrentDate(): String {
     return   localData.getCurrentDate()
    }
}