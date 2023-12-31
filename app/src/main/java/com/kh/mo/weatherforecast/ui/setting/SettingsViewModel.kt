package com.kh.mo.weatherforecast.ui.setting

import androidx.lifecycle.ViewModel
import com.kh.mo.weatherforecast.model.ui.Location
import com.kh.mo.weatherforecast.model.ui.LocationData
import com.kh.mo.weatherforecast.repo.Repo

class SettingsViewModel(private val repo: Repo) : ViewModel() {


    fun setLanguage(language: Language) {
        repo.setLanguage(language)
    }

    fun getLanguage() = repo.getLanguage()


    fun setTempUnit(unit: Units) {
        repo.setTempUnit(unit)
    }

    fun getTempUnit() = repo.getTempUnit()


    fun setLocation(location: Location) {
        repo.setWayOfSelectLocation(location)
    }

    fun getWayOfSelectLocation() = repo.getWayOfSelectLocation()


    fun setWindSpeed(unit: Units) {
        repo.setWindSpeed(unit)
    }

    fun getWindSpeed() = repo.getWindSpeed()


    fun setNotification(isNotification: Boolean) {
        repo.changeNotificationValue(isNotification)
    }

    fun getNotification() = repo.checkIsNotificationAvailable()
    fun saveLocationData(locationData: LocationData) {
        repo.setLat(locationData.lat)
        repo.setLon(locationData.lon)
        locationData.nameOfCity?.let { repo.setCityName(it) }
    }

    fun getAddressLocation(lat: Double, lon: Double,
                           getLocationData:(nameOfCity:String,
                                            nameOfCountry:String)->Unit){
        repo.getAddressLocation(lat, lon, getLocationData)
    }

    fun changeLanguageApp(language: String) {
        repo.changeLanguageApp(language)
    }
}